package com.matome.ledger.account.controller;


import com.matome.ledger.account.util.ResponseHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Account {

    @GetMapping("/balance")
    public ResponseEntity balance() {
        return  null;
    }

    @PostMapping("/credit")
    public ResponseEntity deposit() {
        return null;
    }

    @PostMapping("/debit")
    public ResponseEntity withdraw() {
        return null;
    }

    @PostMapping("/account/create")
    public ResponseEntity createAccount() {
        return  null;
    }

    @DeleteMapping("/account/remove")
    public ResponseEntity removeAccount() {
        return  null;
    }

}
