package com.matome.ledger.account.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.AuditModel;
import com.matome.ledger.account.entities.Transactions;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feature_dated_transactions")
public class FeatureDatedTransactions extends AuditModel {

    public enum STATUS {
        CANCELLED,
        PROCESSED,
        WAITING,
        FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", nullable = false)
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account accountNumber;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Transactions.transactionType transactionType;

    @Column(name = "dated_for", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate datedFor;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private STATUS status;
    @Column(name = "Reference", nullable = false)
    private String reference;
}
