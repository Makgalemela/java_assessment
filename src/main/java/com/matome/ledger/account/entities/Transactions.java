package com.matome.ledger.account.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.AuditModelTransactions;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transctions")
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Transactions  extends AuditModelTransactions {

    public enum transactionType {
        CREDIT,
        DEBIT
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private transactionType transactionType;
    @Column(name = "Reference", nullable = false)
    private String reference;

}
