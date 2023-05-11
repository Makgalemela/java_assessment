package com.matome.ledger.account.model;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseResult {

    private Account account;
    private Double balance;
    private Transactions transactions;
    private  FeatureDatedTransactions featureDatedTransactions;
}
