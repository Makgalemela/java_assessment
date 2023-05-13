package com.matome.ledger.account.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.AuditModelTransactions;
import com.matome.ledger.account.entities.Transactions;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "removed_transactions")
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class RemovedTransactions extends AuditModelTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", nullable = false)
    @JsonBackReference

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
