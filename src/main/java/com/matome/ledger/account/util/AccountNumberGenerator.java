package com.matome.ledger.account.util;

import com.matome.ledger.account.repository.AccountRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;

@Service
public class AccountNumberGenerator  implements IdentifierGenerator {

    private final AccountRepository accountRepository;

    public AccountNumberGenerator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        Long count = accountRepository.count();
        LocalDate currentDateTime = LocalDate.now();
        // 5 char long chars
        String account = currentDateTime.toString()
                .replaceAll("-", "")
                .substring(3);

        // For production ready application we will need to write a better generator
        // we would find a proper algorithm
        // We will also need to deal with concurrency to make sure that no same account numbers is created
        // This is limited to 99999 account at every given day

        String accountNumber = String.format("%05", count);

        return account.concat(accountNumber);
    }

}
