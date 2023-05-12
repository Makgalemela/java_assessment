package com.matome.ledger.account.Dto;


import lombok.*;
import com.matome.ledger.account.entities.Account.AccountStatus;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {

    private Long accountNumber;
    private String firstName;
    private String lastName;
    private AccountStatus status;
}
