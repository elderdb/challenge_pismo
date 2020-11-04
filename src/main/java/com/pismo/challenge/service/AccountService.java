package com.pismo.challenge.service;

import com.pismo.challenge.domain.Account;
import com.pismo.challenge.repository.AccountRepository;
import com.pismo.challenge.utils.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.pismo.challenge.enums.ErrorMessageEnum.DOCUMENT_NUMBER_ALREADY_IN_USE;
import static com.pismo.challenge.enums.ErrorMessageEnum.DOCUMENT_NUMBER_IS_NULL;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  @Autowired
  @Lazy
  public AccountService(final AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Optional<Account> getAccount(final Long accountId) {

    return accountRepository.findById(accountId);

  }

  public Optional<Account> create(final Long documentNumber) {

    if (Objects.isNull(documentNumber)) {
      throw new BadRequestException(DOCUMENT_NUMBER_IS_NULL.getMessage());
    }

    if (accountRepository.findFirstByDocumentNumber(documentNumber).isPresent()) {
      throw new BadRequestException(DOCUMENT_NUMBER_ALREADY_IN_USE.getMessage());
    }

    return Optional.of(accountRepository.saveAndFlush(Account.create(documentNumber)));

  }

}
