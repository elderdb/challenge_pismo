package com.pismo.challenge.service;

import com.pismo.challenge.domain.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

  public Optional<Account> getAccount(final Long accountId) {

    return Optional.empty();

  }

}
