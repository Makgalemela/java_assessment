package com.matome.ledger.account.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.model.Account;
import com.matome.ledger.account.model.Request;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.services.RequestProcessor;
import com.matome.ledger.account.util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/account")
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
    public ResponseEntity deposit() {
        return null;
    }

    @PostMapping("/debit")
    public ResponseEntity withdraw() {
        return null;
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
        }    }

    @DeleteMapping("/remove")
    public ResponseEntity removeAccount() {
        return  null;
    }

}
