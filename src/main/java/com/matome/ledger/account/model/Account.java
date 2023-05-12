package com.matome.ledger.account.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account  extends  AuditModel {

    public  enum AccountStatus {
        ACTIVE,
        INACTIVE
    }
    @Id
    @GenericGenerator(name = "account_number", strategy = "com.matome.ledger.account.util.AccountNumberGenerator")
    @GeneratedValue(generator = "account_number")
    @Column(name="account_number", nullable = false)
    private String accountNumber;
    @Column(name="first_name", nullable = true)
    private String firstName;

    @Column(name="last_name", nullable = true)
    private String lastName;

    @Column(name="account_status", nullable = true)
    private AccountStatus status;
}
