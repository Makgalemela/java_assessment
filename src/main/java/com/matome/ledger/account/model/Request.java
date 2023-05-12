package com.matome.ledger.account.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
