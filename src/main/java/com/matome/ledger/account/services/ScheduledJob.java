package com.matome.ledger.account.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.Dto.TransactionDto;
import com.matome.ledger.account.entities.FeatureDatedTransactions;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.repository.FutureTransactionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ScheduledJob {

    private final FutureTransactionsRepository repository;

    private final AccountInterface accountInterface;

    private final ObjectMapper mapper;

    public ScheduledJob(FutureTransactionsRepository repository, AccountInterface accountInterface, ObjectMapper mapper) {
        this.repository = repository;
        this.accountInterface = accountInterface;
        this.mapper = mapper;
    }

    @Scheduled(cron = "${future.dated.cron.frequency}")
    void processFutureDated() {

        LocalDate today = LocalDate.now();
        Map<String, Object> map = new HashMap<>();

        repository.findByDatedForAndStatus(today, FeatureDatedTransactions.STATUS.WAITING)
                .ifPresent(txns -> txns.stream().forEach(txn ->
                        {
                            try {
                                map.clear();
                                AccountDto account = AccountDto.builder()
                                        .accountNumber(txn.getAccountNumber().getAccountNumber())
                                        .build();

                                ResponseResult response = ResponseResult.builder().build();
                                TransactionDto transactionDto = TransactionDto.builder()
                                        .accountNumber(account)
                                        .amount(txn.getAmount())
                                        .reference(txn.getReference())
                                        .build();

                                response = accountInterface.credit(transactionDto);

                                txn.setStatus(FeatureDatedTransactions.STATUS.PROCESSED);
                                repository.save(txn);
                            } catch (Exception ex) {
                                txn.setStatus(FeatureDatedTransactions.STATUS.FAILED);
                                repository.save(txn);
                                log.info(String.format("%s - Error %s", "Processing Failed", ex.toString()));
                            }
                        }
                ));
    }
}
