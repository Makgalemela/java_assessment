package com.matome.ledger.account.services;

import com.matome.ledger.account.model.*;
import com.matome.ledger.account.repository.AccountRepository;
import com.matome.ledger.account.repository.RemovedTransactionsRepository;
import com.matome.ledger.account.repository.TransactionsRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountImpl implements AccountInterface {

    final AccountRepository accountRepository;
    final TransactionsRepository transactionsRepository;
    final RemovedTransactionsRepository removedTransactionsRepository;

    public AccountImpl(AccountRepository accountRepository, TransactionsRepository transactionsRepository,
                       RemovedTransactionsRepository removedTransactionsRepository) {
        this.accountRepository = accountRepository;
        this.transactionsRepository = transactionsRepository;
        this.removedTransactionsRepository = removedTransactionsRepository;
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
                return featureDateDeposit();
            }

            case BALANCE: {
                return balance(request.getAccount());
            }
            case DELETE_TRANSACTION: {
                return removeTransaction(request.getTransactions());
            }
            default: {

            }
        }

        return null;
    }

    @Override
    public ResponseResult credit(final Transactions transactions) {

        Transactions credit = transactions;
        transactions.setTransactionType(Transactions.transactionType.CREDIT);
        transactionsRepository.save(credit);

        return ResponseResult.builder()
                .transactions(credit)
                .build();
    }

    @Override
    public ResponseResult debit(final Transactions transactions) {
        Transactions credit = transactions;
        transactions.setTransactionType(Transactions.transactionType.DEBIT);
        transactionsRepository.save(credit);
        return ResponseResult.builder()
                .transactions(credit)
                .build();
    }

    @Override
    public ResponseResult createAccount(final Account account) {
        Account newAccount = accountRepository.save(account);
        return ResponseResult.builder()
                .account(newAccount)
                .build();
    }

    @Override
    public ResponseResult balance(final Account account) {

        Optional<List<Transactions>> transactions = transactionsRepository.findByAccountNumber(account);

        if (transactions.isPresent()) {
            List<Transactions> transactionsList = transactions.get();
            Double balance = transactionsList.stream()
                    .mapToDouble(x ->
                            x.getTransactionType().equals(Transactions.transactionType.DEBIT) ?
                                    x.getAmount().doubleValue() : 0 - x.getAmount().doubleValue())
                    .sum();
            return ResponseResult.builder()
                    .balance(balance)
                    .build();
        } else {
            return ResponseResult.builder()
                    .balance(0.00)
                    .build();
        }
    }

    @Override
    public ResponseResult featureDateDeposit() {
        return null;
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
                    .transactions(transactions)
                    .build();
        } else {
            return ResponseResult.builder()
                    .build();
        }

    }

    @Override
    public ResponseResult removeAccount(final Account account) {

        Optional<Account> account1 = accountRepository.findByAccountNumber(Long.valueOf(account.getAccountNumber()));
        List<RemovedTransactions> removedTransactionsList = new ArrayList<>();
        if (account1.isPresent()) {
            Optional<List<Transactions>> transactions = transactionsRepository.findByAccountNumber(account);
            transactions.ifPresent(x ->
                    x.parallelStream().peek(txn -> {
                        RemovedTransactions removedTransaction
                                = RemovedTransactions.builder()
                                .transactionType(txn.getTransactionType())
                                .amount(txn.getAmount())
                                .accountNumber(txn.getAccountNumber())
                                .originalDate(txn.getCreatedAt())
                                .reference(txn.getReference())
                                .build();

                        removedTransactionsList.add(removedTransaction);
                        ;
                    }));
            if (!removedTransactionsList.isEmpty()) {
                removedTransactionsRepository.deleteAll(removedTransactionsList);
                transactionsRepository.deleteAll(transactions.get());
            }
        }
        return null;
    }

}
