package com.matome.ledger.account.Dto;


import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class TransactionListDto implements Serializable {
    private String filter;
    private String pageNumber;
    private String accountNumber;
}
