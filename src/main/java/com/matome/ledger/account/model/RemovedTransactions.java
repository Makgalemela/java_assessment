package com.matome.ledger.account.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "removed_transactions")
public class RemovedTransactions extends AuditModelTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private RemovedAccount accountNumber;
    @Column(name = "credit", nullable = false)
    private BigDecimal credit;
    @Column(name = "debit", nullable = false)
    private BigDecimal debit;
    @Column(name = "credit", nullable = false)
    private LocalDateTime originalDate;
    @Column(name = "Reference", nullable = false)
    private String reference;
}
