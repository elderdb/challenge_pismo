package com.pismo.challenge.controller;

import com.pismo.challenge.domain.Account;
import com.pismo.challenge.domain.Transaction;
import com.pismo.challenge.dto.TransactionDTO;
import com.pismo.challenge.service.AccountService;
import com.pismo.challenge.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/onboard")
public class ChallengeController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final AccountService accountService;
  private final TransactionService transactionService;

  @Autowired
  @Lazy
  public ChallengeController(final AccountService accountService,
                             final TransactionService transactionService) {
    this.accountService = accountService;
    this.transactionService = transactionService;
  }

  @GetMapping(value = "/accounts/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Account> getAccount(@PathVariable final Long accountId) {

    return accountService.getAccount(accountId) //
        .map(ResponseEntity::ok) //
        .orElseGet(() -> ResponseEntity.noContent().build());

  }

  @PostMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Account> createAccount(@RequestBody final Long documentNumber) {

    return accountService.create(documentNumber) //
        .map(ResponseEntity::ok) //
        .orElseGet(() -> ResponseEntity.badRequest().build());

  }

  @PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody final TransactionDTO transactionDTO) {

    return transactionService.create(transactionDTO) //
        .map(ResponseEntity::ok) //
        .orElseGet(() -> ResponseEntity.badRequest().build());

  }

}
