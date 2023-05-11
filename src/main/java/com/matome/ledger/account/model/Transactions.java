package com.matome.ledger.account.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transctions")
public class Transactions  extends AuditModelTransactions {

    public enum transactionType {
        CREDIT,
        DEBIT
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
    private transactionType transactionType;
    @Column(name = "Reference", nullable = false)
    private String reference;

}
