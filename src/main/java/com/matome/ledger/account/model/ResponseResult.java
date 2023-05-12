package com.matome.ledger.account.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseResult implements Serializable {
    private Account account;
    private Double balance;
    private Transactions transactions;
    private FeatureDatedTransactions featureDatedTransactions;
}
