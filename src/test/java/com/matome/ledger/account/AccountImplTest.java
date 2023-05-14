package com.matome.ledger.account;


import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.repository.AccountRepository;
import com.matome.ledger.account.services.AccountImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;


import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
public class AccountImplTest {

    @Mock
    private AccountRepository repository;


    @InjectMocks
    private AccountImpl accountImp;

    @Test
    public void test_when_create_account_successfully() {

        AccountDto account = AccountDto.builder().accountNumber(1234567890L).build();

        doReturn(account).when(repository.save(new Account()));

        accountImp.createAccount(account);
        Assertions.assertNull(account, "The saved account should not be null");
    }

}
