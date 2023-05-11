package com.matome.ledger.account.services;

import com.matome.ledger.account.model.Account;
import com.matome.ledger.account.model.FeatureDatedTransactions;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.model.Transactions;

public interface AccountInterface {

    public ResponseResult credit(final Transactions transactions);
    public ResponseResult debit(final Transactions transactions);
    public ResponseResult createAccount(final Account account);
    public ResponseResult balance(final Account account);
    public ResponseResult featureDateDeposit(final FeatureDatedTransactions transactions);
    public ResponseResult removeTransaction(final Transactions transactions);
    public ResponseResult removeAccount(final Account account);

}
