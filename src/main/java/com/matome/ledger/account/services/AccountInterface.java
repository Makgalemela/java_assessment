package com.matome.ledger.account.services;

import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.Dto.FutureDatedTransactionsDto;
import com.matome.ledger.account.Dto.TransactionDto;
import com.matome.ledger.account.Dto.TransactionListDto;
import com.matome.ledger.account.model.ResponseResult;

public interface AccountInterface {

    public ResponseResult credit(final TransactionDto transactions);
    public ResponseResult debit(final TransactionDto transactions);
    public ResponseResult createAccount(final AccountDto account);
    public ResponseResult balance(final AccountDto account);
    public ResponseResult featureDateDeposit(final FutureDatedTransactionsDto transactions);
    public ResponseResult removeTransaction(final TransactionDto transactions);
    public ResponseResult removeAccount(final AccountDto account);
    public  ResponseResult transactions(final TransactionListDto transactionListDto);

}
