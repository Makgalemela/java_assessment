package com.matome.ledger.account.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "account")
public class Account  extends  AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
