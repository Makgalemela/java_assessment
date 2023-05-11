package com.matome.ledger.account.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "removed_account")
public class RemovedAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
