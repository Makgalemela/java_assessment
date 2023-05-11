package com.matome.ledger.account.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "removed_account")
public class RemovedAccount extends AuditModelTransactions {
    @Id
    @Column(name="account_number", nullable = false)
    private String accountNumber;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="create_on", nullable = false)
    private LocalDateTime create_on;

}
