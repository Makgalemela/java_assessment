package com.matome.ledger.account.Dto;

import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.Transactions;
import lombok.*;

import java.math.BigDecimal;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Account accountNumber;
    private BigDecimal amount;
    private Transactions.transactionType transactionType;
    private String reference;
}
