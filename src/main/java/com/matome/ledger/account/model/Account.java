package com.matome.ledger.account.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "account")
public class Account  extends  AuditModel {

    @Id
    @GenericGenerator(name = "account_number", strategy = "com.matome.ledger.account.util.AccountNumberGenerator")
    @GeneratedValue(generator = "account_number")
    @Column(name="account_number", nullable = false)
    private String accountNumber;
    @Column(name="first_name", nullable = true)
    private String firstName;

    @Column(name="last_name", nullable = true)
    private String lastName;
}
