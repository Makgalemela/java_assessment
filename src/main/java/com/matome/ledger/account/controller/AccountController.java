package com.matome.ledger.account.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.Dto.TransactionDto;
import com.matome.ledger.account.Dto.TransactionListDto;
import com.matome.ledger.account.Dto.TransactionRequestDto;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.entities.Transactions;
import com.matome.ledger.account.model.Request;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.services.RequestProcessor;
import com.matome.ledger.account.util.ACCOUNT;
import com.matome.ledger.account.util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/ledger")
public class AccountController {
    final RequestProcessor requestProcessor;
    final ObjectMapper mapper;


    public AccountController(RequestProcessor requestProcessor, ObjectMapper mapper) {
        this.requestProcessor = requestProcessor;
        this.mapper = mapper;
    }

    @GetMapping("/balance/{account_number}")
    public ResponseEntity<Object> balance(@PathVariable("account_number")  @ACCOUNT String accountNumber) {

        AccountDto account = AccountDto.builder().accountNumber(Long.valueOf(accountNumber)).build();
        Request request = Request.builder().account(account).requestType(Request.RequestType.BALANCE).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true,
                    Objects.isNull(response.getAccount()) ? "Account Does not Exist"
                   : "Account Balance", response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/credit/{account_number}")
    public ResponseEntity<Object> deposit(@RequestBody TransactionRequestDto transactionDto, @PathVariable("account_number")
     @ACCOUNT String accountNumber) {

        TransactionDto transaction = mapper.convertValue(transactionDto, TransactionDto.class);
        AccountDto accountDto = AccountDto.builder().accountNumber(Long.valueOf(accountNumber)).build();
        transaction.setAccountNumber(accountDto);
        Request request = Request.builder().transactions(transaction).requestType(Request.RequestType.CREDIT).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Account Credited", response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/debit/{account_number}")
    public ResponseEntity<Object> withdraw(@RequestBody TransactionDto transactionDto, @PathVariable("account_number")
    @ACCOUNT String accountNumber) {
        TransactionDto transaction = mapper.convertValue(transactionDto, TransactionDto.class);
        AccountDto accountDto = AccountDto.builder().accountNumber(Long.valueOf(accountNumber)).build();
        transaction.setAccountNumber(accountDto);
        Request request = Request.builder().transactions(transaction).requestType(Request.RequestType.DEBIT).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Account Debited", response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAccount(@RequestBody AccountDto account) {

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
                                                    @PathVariable("account_number") @ACCOUNT String accountNumber) {

        AccountDto accountDto = AccountDto.builder().accountNumber(Long.valueOf(accountNumber)).build();
        TransactionDto transactions = TransactionDto.builder().accountNumber(accountDto).id(transactionId).build();
        Request request = Request.builder().transactions(transactions)
                .requestType(Request.RequestType.DELETE_TRANSACTION).build();
        try {
            requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Removed Transaction Successfully.");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @DeleteMapping("/remove/account/{account_number}")
    public ResponseEntity<Object> removeAccount(@PathVariable("account_number")  @ACCOUNT Long accountNumber) {
        AccountDto account = AccountDto.builder().accountNumber(Long.valueOf(accountNumber)).build();
        Request request = Request.builder().account(account).requestType(Request.RequestType.DELETE_ACCOUNT).build();
        try {
            requestProcessor.processRequest(request);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Removed Account Successfully.");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

    @GetMapping("/transactions/{account_number}")
    public ResponseEntity<Object> getTransactions(@PathVariable("account_number") @ACCOUNT Long accountNumber,
                                                  @RequestParam("transaction_type") Optional<String> transactionType) {

        String transactionTypePresent = transactionType.orElse(null);
        TransactionListDto transactionListDto = TransactionListDto
                .builder().filter(transactionTypePresent).accountNumber(String.valueOf(accountNumber)).build();
        Request request = Request.builder().transactionListDto(transactionListDto)
                .requestType(Request.RequestType.GET_TRANSACTIONS).build();
        try {
            ResponseResult response = requestProcessor.processRequest(request);

            if(Objects.isNull(response.getAccount())) {
                return ResponseHandler.generateResponse(HttpStatus.OK, true, "Account Not found" , response);
            } else  {
                return ResponseHandler.generateResponse(HttpStatus.OK, true,
                        response.getTransactions().isEmpty() ? "No Result Found" : "Successful", response);
            }

        } catch (Exception ex) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage());
        }
    }

}
