package com.matome.ledger.account.Dto;

import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.Transactions;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto  implements Serializable {
    private BigDecimal amount;
    private String reference;
}
