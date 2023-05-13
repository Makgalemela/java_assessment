package com.matome.ledger.account.repository;


import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    Optional<List<Transactions>> findByAccountNumber(final Account account);

    Optional<List<Transactions>> findByAccountNumberAndAccountNumber_Status(final Account account, Account.AccountStatus status);

    Optional<List<Transactions>> findAllByAccountNumberAndTransactionType(final Account account, final Transactions.transactionType transactionType);
}
