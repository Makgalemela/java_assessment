package com.matome.ledger.account.repository;


import com.matome.ledger.account.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumberAndStatus(Long id, Account.AccountStatus status);
    Optional<Account> findByAccountNumber(Long id);
}
