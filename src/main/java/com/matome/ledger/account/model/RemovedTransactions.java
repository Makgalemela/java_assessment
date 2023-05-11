package com.matome.ledger.account.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "removed_transactions")
public class RemovedTransactions extends  AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
