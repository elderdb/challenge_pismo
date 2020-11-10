package com.pismo.challenge.builder;

import com.pismo.challenge.domain.Account;
import com.pismo.challenge.domain.OperationType;
import com.pismo.challenge.domain.Transaction;
import com.pismo.challenge.dto.TransactionDTO;
import com.pismo.challenge.enums.OperationTypeDescription;

public class TransactionServiceBuilder {

  public static TransactionDTO createDTO() {

    final TransactionDTO transactionDTO = new TransactionDTO();

    transactionDTO.setAccountId(1L);
    transactionDTO.setAmount(1000.0);
    transactionDTO.setOperationTypeId(4);

    return transactionDTO;

  }

  public static Account createAccount() {

    final Account account = new Account();

    account.setAvailableCreditLimit(1000.0);
    account.setAccountId(4L);
    account.setDocumentNumber(123456789L);

    return account;

  }

  public static OperationType createOperationType() {

    final OperationType operationType = new OperationType();

    operationType.setDescription(OperationTypeDescription.PAGAMENTO);
    operationType.setOperationTypeId(4);

    return operationType;

  }

  public static Transaction createTransaction(final Account account,
                                              final OperationType operationType,
                                              final Double amount) {

    final Transaction transaction = new Transaction();

    transaction.setOperationType(operationType);
    transaction.setAmount(amount);
    transaction.setAccount(account);
    transaction.setTransactionId(1L);

    return transaction;

  }

}
