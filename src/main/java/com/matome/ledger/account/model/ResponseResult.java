package com.matome.ledger.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.FeatureDatedTransactions;
import com.matome.ledger.account.entities.Transactions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

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
    private Transactions transaction;
    private FeatureDatedTransactions featureDatedTransactions;
    private List<Transactions> transactions;

}
