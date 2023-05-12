package com.matome.ledger.account.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "removed_transactions")
@AllArgsConstructor
@NoArgsConstructor
public class RemovedTransactions extends AuditModelTransactions {

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
    @Column(name = "credit", nullable = false)
    private LocalDateTime originalDate;
    @Column(name = "Reference", nullable = false)
    private String reference;
}
