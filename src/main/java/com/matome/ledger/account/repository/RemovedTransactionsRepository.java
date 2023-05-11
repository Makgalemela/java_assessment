package com.matome.ledger.account.repository;

import com.matome.ledger.account.model.RemovedTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemovedTransactionsRepository extends JpaRepository<RemovedTransactions, Long> {

}
