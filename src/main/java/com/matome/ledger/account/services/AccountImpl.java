package com.matome.ledger.account.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.Dto.TransactionListDto;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.FeatureDatedTransactions;
import com.matome.ledger.account.entities.RemovedTransactions;
import com.matome.ledger.account.entities.Transactions;
import com.matome.ledger.account.model.Request;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.repository.AccountRepository;
import com.matome.ledger.account.repository.FutureTransactionsRepository;
import com.matome.ledger.account.repository.RemovedTransactionsRepository;
import com.matome.ledger.account.repository.TransactionsRepository;
import com.matome.ledger.account.util.AccountNumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AccountImpl implements AccountInterface {

    final AccountRepository accountRepository;
    final TransactionsRepository transactionsRepository;
    final RemovedTransactionsRepository removedTransactionsRepository;

    final AccountNumberGenerator accountNumberGenerator;

    final FutureTransactionsRepository futureTransactionsRepository;

    final ObjectMapper mapper;

    public AccountImpl(AccountRepository accountRepository, TransactionsRepository transactionsRepository,
                       RemovedTransactionsRepository removedTransactionsRepository, AccountNumberGenerator accountNumberGenerator, FutureTransactionsRepository futureTransactionsRepository, ObjectMapper mapper) {
        this.accountRepository = accountRepository;
        this.transactionsRepository = transactionsRepository;
        this.removedTransactionsRepository = removedTransactionsRepository;
        this.accountNumberGenerator = accountNumberGenerator;
        this.futureTransactionsRepository = futureTransactionsRepository;
        this.mapper = mapper;
    }

    @RabbitListener(queues = "#{serviceQueueInfo.name}", concurrency = "#{serviceQueueConsumers}")
    public ResponseResult dequeueAndProcessRequest(final Request request, Message message) throws Exception {

        switch (request.getRequestType()) {
            case CREATE_ACCOUNT: {

                return createAccount(request.getAccount());
            }
            case CREDIT: {
                return credit(request.getTransactions());
            }
            case DEBIT: {
                return debit(request.getTransactions());
            }
            case DELETE_ACCOUNT: {
                return removeAccount(request.getAccount());
            }
            case FUTURE_DATE: {
                return featureDateDeposit(request.getFeatureDatedTransactions());
            }
            case BALANCE: {
                return balance(request.getAccount());
            }
            case DELETE_TRANSACTION: {
                return removeTransaction(request.getTransactions());
            }
            case GET_TRANSACTIONS: {
                return transactions(request.getTransactionListDto());
            }
            default: {

            }
        }

        return null;
    }

    @Override
    public ResponseResult credit(final Transactions transactions) {

        Transactions credit = transactions;
        credit.setTransactionType(Transactions.transactionType.CREDIT);
        transactionsRepository.save(credit);

        return ResponseResult.builder()
                .transaction(credit)
                .build();
    }

    @Override
    public ResponseResult debit(final Transactions transactions) {
        Transactions debit = transactions;
        debit.setTransactionType(Transactions.transactionType.DEBIT);
        transactionsRepository.save(debit);
        return ResponseResult.builder()
                .transaction(debit)
                .build();
    }

    @Override
    public ResponseResult createAccount(final AccountDto accountDto) {
        Account account = mapper.convertValue(accountDto, Account.class);
        Account newAccount = accountRepository.save(account);
        return ResponseResult.builder()
                .account(newAccount)
                .build();
    }

    @Override
    public ResponseResult balance(final AccountDto accountDto) {
        Optional<List<Transactions>> transactions = Optional.empty();

        Account account = accountRepository.findByAccountNumberAndStatus(accountDto.getAccountNumber(),
                        Account.AccountStatus.ACTIVE).orElse(null);

        if(Objects.nonNull(account)) {
            transactions = transactionsRepository.findByAccountNumber(account);
        } else {
            return ResponseResult.builder()
                    .account(null)
                    .build();
        }
        if (transactions.isPresent()) {
            List<Transactions> transactionsList = transactions.get();
            Double balance = transactionsList.stream()
                    .mapToDouble(x ->
                            x.getTransactionType().equals(Transactions.transactionType.DEBIT) ?
                                    0 - x.getAmount().doubleValue() : x.getAmount().doubleValue())
                    .sum();
            return ResponseResult.builder()
                    .balance(balance)
                    .account(account)
                    .build();
        } else {
            return ResponseResult.builder()
                    .balance(0.00)
                    .account(account)
                    .build();
        }
    }

    @Override
    public ResponseResult featureDateDeposit(final FeatureDatedTransactions transactions) {

        FeatureDatedTransactions featureDatedTransactions = transactions;
        transactions.setTransactionType(Transactions.transactionType.DEBIT);
        futureTransactionsRepository.save(featureDatedTransactions);
        return ResponseResult.builder()
                .featureDatedTransactions(featureDatedTransactions)
                .build();
    }

    @Override
    @Transactional
    public ResponseResult removeTransaction(final Transactions transactions) {

        Optional<Transactions> transaction = transactionsRepository.findById(transactions.getId());
        if (transaction.isPresent()) {
            Transactions txn = transaction.get();
            RemovedTransactions removedTransactions
                    = RemovedTransactions.builder()
                    .transactionType(txn.getTransactionType())
                    .amount(txn.getAmount())
                    .accountNumber(txn.getAccountNumber())
                    .originalDate(txn.getCreatedAt())
                    .reference(txn.getReference())
                    .build();

            removedTransactionsRepository.save(removedTransactions);
            transactionsRepository.delete(transactions);

            return ResponseResult.builder()
                    .transaction(transactions)
                    .build();
        } else {
            return ResponseResult.builder()
                    .build();
        }

    }

    @Override
    public ResponseResult removeAccount(final AccountDto accountDto) {

        Account account = mapper.convertValue(accountDto, Account.class);
        Optional<Account> account1 = accountRepository.findByAccountNumberAndStatus(Long.valueOf(account.getAccountNumber()),
                Account.AccountStatus.ACTIVE);
        List<RemovedTransactions> removedTransactionsList = new ArrayList<>();
        if (account1.isPresent()) {
            Optional<List<Transactions>> transactions = transactionsRepository.findByAccountNumber(account);
            transactions.ifPresent(x ->
                    x.parallelStream().forEach(txn -> {
                        RemovedTransactions removedTransaction
                                = RemovedTransactions.builder()
                                .transactionType(txn.getTransactionType())
                                .amount(txn.getAmount())
                                .accountNumber(txn.getAccountNumber())
                                .originalDate(txn.getCreatedAt())
                                .reference(txn.getReference())
                                .build();

                        removedTransactionsList.add(removedTransaction);
                    }));
            if (!removedTransactionsList.isEmpty()) {
                removedTransactionsRepository.saveAll(removedTransactionsList);
                transactionsRepository.deleteAll(transactions.get());
            }
            account1.get().setStatus(Account.AccountStatus.INACTIVE);
            accountRepository.save(account1.get());
            return ResponseResult.builder()
                    .account(account1.get())
                    .build();
        }
        return ResponseResult.builder()
                .build();

    }

    @Override
    public ResponseResult transactions(TransactionListDto transactionListDto) {
        Optional<Account> account = accountRepository.findByAccountNumberAndStatus(Long.valueOf(transactionListDto.getAccountNumber()),
                Account.AccountStatus.ACTIVE);
        Optional<List<Transactions>> transactions = Optional.empty();

        if (Objects.isNull(transactionListDto.getFilter()) && account.isPresent()) {
            transactions = transactionsRepository.findByAccountNumber(account.get());
        } else if (account.isPresent() && (transactionListDto.getFilter().equalsIgnoreCase(String.valueOf(Transactions.transactionType.CREDIT)) ||
                transactionListDto.getFilter().equalsIgnoreCase(String.valueOf(Transactions.transactionType.DEBIT)))) {
            transactions = transactionsRepository.findAllByAccountNumberAndTransactionType(account.get(),
                    Transactions.transactionType.valueOf(transactionListDto.getFilter()));

        }
        if (!account.isPresent()) {
            return ResponseResult.builder()
                    .build();
        } else if (transactions.isPresent() && !transactions.get().isEmpty()) {
            return ResponseResult.builder()
                    .transactions(transactions.get())
                    .account(account.get())
                    .build();
        } else {
            return ResponseResult.builder()
                    .transactions(new ArrayList<Transactions>())
                    .account(account.get())
                    .build();
        }
    }
}
