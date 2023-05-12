package com.matome.ledger.account.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/*
    We need to be able to retain future date transaction
    we can come uo with business rules to determine how long transaction need to stay in
    feature_dated_transactions tables

    - ideally we should have 4 tables,
    - 1. for cancelled future dated
    - 2. for waiting to be processed
    - 3. for processed successfully
    - 4. for failed processing with insufficient balance
    by separating the tables we will get the best performance
 */
@Data
@Entity
@Table(name = "feature_dated_transactions")
public class FeatureDatedTransactions extends AuditModel {

    public enum STATUS {
        CANCELLED,
        PROCESSED,
        WAITING,
        INSUFFICIENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private Account accountNumber;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Transactions.transactionType transactionType;

    @Column(name = "dated_for", nullable = false)
    private LocalDate datedFor;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private STATUS status;
}
