package com.matome.ledger.account.Dto;

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
    private AccountDto accountNumber;
    private Long id;
    private BigDecimal amount;
    private String reference;
}
