package com.pismo.challenge.service;

import com.pismo.challenge.domain.Account;
import com.pismo.challenge.domain.OperationType;
import com.pismo.challenge.domain.Transaction;
import com.pismo.challenge.dto.TransactionDTO;
import com.pismo.challenge.enums.OperationTypeDescription;
import com.pismo.challenge.repository.AccountRepository;
import com.pismo.challenge.repository.OperationTypeRepository;
import com.pismo.challenge.repository.TransactionRepository;
import com.pismo.challenge.utils.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.pismo.challenge.enums.ErrorMessageEnum.*;
import static com.pismo.challenge.enums.OperationTypeDescription.*;

@Service
public class TransactionService {

  static final List<OperationTypeDescription> withdrawEnums = Arrays.asList(COMPRA_VISTA, COMPRA_PARCELADA, SAQUE);
  static final List<OperationTypeDescription> depositEnums = Collections.singletonList(PAGAMENTO);
  private final AccountRepository accountRepository;
  private final OperationTypeRepository operationTypeRepository;
  private final TransactionRepository transactionRepository;

  @Autowired
  @Lazy
  public TransactionService(final AccountRepository accountRepository,
                            final OperationTypeRepository operationTypeRepository,
                            final TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.operationTypeRepository = operationTypeRepository;
    this.transactionRepository = transactionRepository;
  }

  private static Double getAmount(final OperationType operationType, final Double amount) {

    if (withdrawEnums.contains(operationType.getDescription())) {
      return -amount;
    }

    if (depositEnums.contains(operationType.getDescription())) {
      return amount;
    }

    throw new BadRequestException(OPERATION_TYPE_IS_INVALID.getMessage());

  }

  public Optional<Transaction> create(final TransactionDTO transactionDTO) {

    if (Objects.isNull(transactionDTO)) {
      throw new BadRequestException(PAYLOAD_CANNOT_BE_NULL.getMessage());
    }

    if (transactionDTO.getAmount() <= 0) {
      throw new BadRequestException(AMOUNT_MUST_BE_GREATER_THAN_ZERO.getMessage());
    }

    final Account account = accountRepository.findById(transactionDTO.getAccountId())
        .orElseThrow(() -> new BadRequestException(ACCOUNT_NOT_FOUND.getMessage()));

    final OperationType operationType = operationTypeRepository.findById(transactionDTO.getOperationTypeId())
        .orElseThrow(() -> new BadRequestException(OPERATION_TYPE_IS_INVALID.getMessage()));

    if (withdrawEnums.contains(operationType.getDescription())) {

      if (hasAvailableCreditLimit(account, transactionDTO.getAmount())) {
        account.setAvailableCreditLimit(account.getAvailableCreditLimit() - transactionDTO.getAmount());
      } else {
        throw new BadRequestException(NO_AVAILABLE_CREDIT_LIMIT.getMessage());
      }

    }

    if (depositEnums.contains(operationType.getDescription())) {
      account.setAvailableCreditLimit(account.getAvailableCreditLimit() + transactionDTO.getAmount());
    }

    accountRepository.saveAndFlush(account);

    return Optional.of(transactionRepository.saveAndFlush(Transaction.create(account, operationType,
        getAmount(operationType, transactionDTO.getAmount()))));

  }

  private boolean hasAvailableCreditLimit(final Account account,
                                          final Double amount) {

    return account.getAvailableCreditLimit() - amount >= 0;

  }

}
