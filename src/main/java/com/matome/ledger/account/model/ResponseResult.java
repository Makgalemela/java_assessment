package com.matome.ledger.account.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult implements Serializable {
    private Account account;
    private Double balance;
    private Transactions transactions;
    private FeatureDatedTransactions featureDatedTransactions;
}
