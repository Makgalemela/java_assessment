package com.matome.ledger.account.repository;


import com.matome.ledger.account.entities.FeatureDatedTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FutureTransactionsRepository extends JpaRepository<FeatureDatedTransactions, Long> {

}
