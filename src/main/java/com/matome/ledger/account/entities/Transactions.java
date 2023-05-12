package com.matome.ledger.account.entities;


import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.AuditModelTransactions;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    private transactionType transactionType;
    @Column(name = "Reference", nullable = false)
    private String reference;

}
