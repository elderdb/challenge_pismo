package com.pismo.challenge.controller;

import com.pismo.challenge.domain.Account;
import com.pismo.challenge.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/onboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChallengeController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final AccountService accountService;

  @Autowired
  @Lazy
  public ChallengeController(final AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping(value = "/accounts/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Account> getAccount(@PathVariable final Long accountId) {

    return accountService.getAccount(accountId) //
        .map(ResponseEntity::ok) //
        .orElseGet(() -> ResponseEntity.noContent().build());

  }

}
