package com.matome.ledger.account.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto implements Serializable {
    private BigDecimal amount;
    private String reference;
}
