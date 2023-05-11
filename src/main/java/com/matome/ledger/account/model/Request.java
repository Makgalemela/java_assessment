package com.matome.ledger.account.model;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {

    public enum RequestType {
        CREATE_ACCOUNT,
        BALANCE,
        CREDIT,
        DEBIT,
        DELETE_ACCOUNT,
        FUTURE_DATE,
        DELETE_TRANSACTION
    }

    private RequestType requestType;
    private Account account;
    private FeatureDatedTransactions featureDatedTransactions;
    private Transactions transactions;
}
