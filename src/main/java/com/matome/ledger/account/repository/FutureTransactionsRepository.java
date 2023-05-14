package com.matome.ledger.account.repository;


import com.matome.ledger.account.entities.FeatureDatedTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface FutureTransactionsRepository extends JpaRepository<FeatureDatedTransactions, Long> {

    Optional<List<FeatureDatedTransactions>> findByDatedForAndStatus(LocalDate date, FeatureDatedTransactions.STATUS status);
}
