package com.matome.ledger.account.services;

import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.Dto.TransactionListDto;
import com.matome.ledger.account.entities.FeatureDatedTransactions;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.entities.Transactions;

public interface AccountInterface {

    public ResponseResult credit(final Transactions transactions);
    public ResponseResult debit(final Transactions transactions);
    public ResponseResult createAccount(final AccountDto account);
    public ResponseResult balance(final AccountDto account);
    public ResponseResult featureDateDeposit(final FeatureDatedTransactions transactions);
    public ResponseResult removeTransaction(final Transactions transactions);
    public ResponseResult removeAccount(final AccountDto account);
    public  ResponseResult transactions(final TransactionListDto transactionListDto);

}
