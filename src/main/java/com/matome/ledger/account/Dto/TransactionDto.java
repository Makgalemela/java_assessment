package com.matome.ledger.account.Dto;

import com.matome.ledger.account.model.Account;
import com.matome.ledger.account.model.Transactions;
import lombok.*;

import javax.persistence.Column;
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
