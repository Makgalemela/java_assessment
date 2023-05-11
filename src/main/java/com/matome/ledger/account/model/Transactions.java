package com.matome.ledger.account.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transctions")
public class Transactions  extends AuditModelTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private Account accountNumber;
    @Column(name = "credit", nullable = false)
    private BigDecimal credit;
    @Column(name = "debit", nullable = false)
    private BigDecimal debit;

}
