package com.matome.ledger.account.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.matome.ledger.account.Dto.TransactionListDto;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.FeatureDatedTransactions;
import com.matome.ledger.account.entities.Transactions;
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
        DELETE_TRANSACTION,
        GET_TRANSACTIONS
    }

    private RequestType requestType;
    private Account account;
    private FeatureDatedTransactions featureDatedTransactions;
    private Transactions transactions;
    private TransactionListDto transactionListDto;
}
