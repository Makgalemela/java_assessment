package com.matome.ledger.account.Dto;


import lombok.*;
import com.matome.ledger.account.model.Account.AccountStatus;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto  {

    private String accountNumber;
    private String firstName;
    private String lastName;
    private AccountStatus status;
}
