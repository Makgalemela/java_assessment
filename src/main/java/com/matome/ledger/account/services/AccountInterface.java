package com.matome.ledger.account.services;

import com.matome.ledger.account.util.ResponseHandler;

public interface AccountInterface {

    public ResponseHandler deposit();
    public ResponseHandler createAccount();
    public ResponseHandler balance();
    public ResponseHandler featureDateDeposit();
    public ResponseHandler removeTransaction();
    public ResponseHandler removeAccount();

}
