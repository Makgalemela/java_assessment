package com.matome.ledger.account.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transctions")
public class Transactions  extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
