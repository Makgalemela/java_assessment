package com.matome.ledger.account.Dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class TransactionListDto {
    private String filter;
    private String pageNumber;
    private String accountNumber;
}
