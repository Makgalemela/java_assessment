package com.matome.ledger.account.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.Dto.TransactionDto;
import com.matome.ledger.account.model.Account;
import com.matome.ledger.account.model.Request;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.model.Transactions;
import com.matome.ledger.account.services.RequestProcessor;
import com.matome.ledger.account.util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/ledger")
public class AccountController {
    final RequestProcessor requestProcessor;
    public AccountController(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity balance(@PathVariable String accountNumber) {

        Account account = Account.builder().accountNumber(accountNumber).build();
        Request request = Request.builder().account(account).requestType(Request.RequestType.BALANCE).build();
        try {
            ResponseResult response =  requestProcessor.processRequest(request);
          return   ResponseHandler.generateResponse(HttpStatus.OK, true, "Successful", response);
        } catch (Exception ex) {
            return   ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/credit")
    public ResponseEntity deposit(@RequestBody TransactionDto transactionDto) {

        ObjectMapper mapper = new ObjectMapper();
        Transactions transactions = mapper.convertValue(transactionDto, Transactions.class);
        Request request = Request.builder().transactions(transactions).requestType(Request.RequestType.CREDIT).build();
        try {
            ResponseResult response =  requestProcessor.processRequest(request);
            return   ResponseHandler.generateResponse(HttpStatus.OK, true, "Successful", response);
        } catch (Exception ex) {
            return   ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/debit")
    public ResponseEntity withdraw(@RequestBody TransactionDto transactionDto) {

        ObjectMapper mapper = new ObjectMapper();
        Transactions transactions = mapper.convertValue(transactionDto, Transactions.class);
        Request request = Request.builder().transactions(transactions).requestType(Request.RequestType.DEBIT).build();
        try {
            ResponseResult response =  requestProcessor.processRequest(request);
            return   ResponseHandler.generateResponse(HttpStatus.OK, true, "Successful", response);
        } catch (Exception ex) {
            return   ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestBody AccountDto accountDto) {

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.convertValue(accountDto, Account.class);
        Request request = Request.builder().account(account).requestType(Request.RequestType.CREATE_ACCOUNT).build();
        try {
            ResponseResult response =  requestProcessor.processRequest(request);
            return   ResponseHandler.generateResponse(HttpStatus.OK, true, "Successful", response);
        } catch (Exception ex) {
            return   ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @DeleteMapping("/remove/transaction/{transactionId}/{accountNumber}")
    public ResponseEntity removeTransaction(@PathVariable Long transactionId, @PathVariable String accountNumber) {
        Transactions transactions = Transactions.builder().id(transactionId).build();
        Account account = Account.builder().accountNumber(accountNumber).build();
        Request request = Request.builder().transactions(transactions).account(account).requestType(Request.RequestType.DELETE_TRANSACTION).build();
        try {
            ResponseResult response =  requestProcessor.processRequest(request);
            return   ResponseHandler.generateResponse(HttpStatus.OK, true, "Successful", response);
        } catch (Exception ex) {
            return   ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @DeleteMapping("/remove/account/{accountNumber}")
    public ResponseEntity removeAccount(@PathVariable String accountNumber) {
        Account account = Account.builder().accountNumber(accountNumber).build();
        Request request = Request.builder().account(account).requestType(Request.RequestType.DELETE_ACCOUNT).build();
        try {
            ResponseResult response =  requestProcessor.processRequest(request);
            return   ResponseHandler.generateResponse(HttpStatus.OK, true, "Successful", response);
        } catch (Exception ex) {
            return   ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

}
