package com.matome.ledger.account.repository;


import com.matome.ledger.account.model.Account;
import com.matome.ledger.account.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    Optional<List<Transactions>> findByAccountNumber(final Account account);
}
