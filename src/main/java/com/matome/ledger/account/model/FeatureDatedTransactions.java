package com.matome.ledger.account.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "feature_dated_transactions")
public class FeatureDatedTransactions extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
