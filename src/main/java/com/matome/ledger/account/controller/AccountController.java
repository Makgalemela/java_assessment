package com.matome.ledger.account.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.Dto.TransactionDto;
import com.matome.ledger.account.Dto.TransactionListDto;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.Transactions;
import com.matome.ledger.account.model.Request;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.services.RequestProcessor;
import com.matome.ledger.account.util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/ledger")
public class AccountController {
    final RequestProcessor requestProcessor;

    public AccountController(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @GetMapping("/balance/{account_number}")
    public ResponseEntity<Object> balance(@PathVariable("account_number") String accountNumber) {

        Account account = Account.builder().accountNumber(Long.valueOf(accountNumber)).build();
        Request request = Request.builder().account(account).requestType(Request.RequestType.BALANCE).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Account Balance", response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/credit")
    public ResponseEntity<Object> deposit(@RequestBody TransactionDto transactionDto) {

        ObjectMapper mapper = new ObjectMapper();
        Transactions transactions = mapper.convertValue(transactionDto, Transactions.class);
        Request request = Request.builder().transactions(transactions).requestType(Request.RequestType.CREDIT).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Account Credited", response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/debit")
    public ResponseEntity<Object> withdraw(@RequestBody TransactionDto transactionDto) {

        ObjectMapper mapper = new ObjectMapper();
        Transactions transactions = mapper.convertValue(transactionDto, Transactions.class);
        Request request = Request.builder().transactions(transactions).requestType(Request.RequestType.DEBIT).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Account Debited", response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAccount(@RequestBody AccountDto accountDto) {

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.convertValue(accountDto, Account.class);
        Request request = Request.builder().account(account).requestType(Request.RequestType.CREATE_ACCOUNT).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "Account Created", response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @DeleteMapping("/remove/transaction/{transaction_id}/{account_number}")
    public ResponseEntity<Object> removeTransaction(@PathVariable("transaction_id") Long transactionId,
                                                    @PathVariable("account_number") String accountNumber) {
        Transactions transactions = Transactions.builder().id(transactionId).build();
        Account account = Account.builder().accountNumber(Long.valueOf(accountNumber)).build();
        Request request = Request.builder().transactions(transactions).account(account).requestType(Request.RequestType.DELETE_TRANSACTION).build();
        try {
            requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Removed Transaction Successfully.");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @DeleteMapping("/remove/account/{account_number}")
    public ResponseEntity<Object> removeAccount(@PathVariable("account_number") String accountNumber) {
        Account account = Account.builder().accountNumber(Long.valueOf(accountNumber)).build();
        Request request = Request.builder().account(account).requestType(Request.RequestType.DELETE_ACCOUNT).build();
        try {
            requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Removed Account Successfully.");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @GetMapping("/transactions/{account_number}")
    public ResponseEntity<Object> getTransactions(@PathVariable("account_number") String accountNumber, @RequestParam("transaction_type") Optional<String> transactionType) {

        String transactionTypePresent = transactionType.isPresent() ? transactionType.get() : null;
        TransactionListDto transactionListDto = TransactionListDto
                .builder().filter(transactionTypePresent).accountNumber(accountNumber).build();
        Request request = Request.builder().transactionListDto(transactionListDto)
                .requestType(Request.RequestType.GET_TRANSACTIONS).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);

            return ResponseHandler.generateResponse(
                    response.getTransactions().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK
                    , true,
                    response.getTransactions().isEmpty() ? "No Records Found" : "Successful", response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

}
