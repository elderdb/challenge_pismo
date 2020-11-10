package com.pismo.challenge.service;

import com.pismo.challenge.builder.TransactionServiceBuilder;
import com.pismo.challenge.domain.Account;
import com.pismo.challenge.domain.OperationType;
import com.pismo.challenge.dto.TransactionDTO;
import com.pismo.challenge.enums.OperationTypeDescription;
import com.pismo.challenge.repository.AccountRepository;
import com.pismo.challenge.repository.OperationTypeRepository;
import com.pismo.challenge.repository.TransactionRepository;
import com.pismo.challenge.utils.exception.BadRequestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TransactionService.class})
public class TransactionServiceTest {

  private final AccountRepository accountRepository = PowerMockito.mock(AccountRepository.class);
  private final OperationTypeRepository operationTypeRepository = PowerMockito.mock(OperationTypeRepository.class);
  private final TransactionRepository transactionRepository = PowerMockito.mock(TransactionRepository.class);

  private final TransactionService transactionService =
      PowerMockito.spy(new TransactionService(accountRepository, operationTypeRepository, transactionRepository));

  @Test
  public void shouldCreateANewTransaction() {

    final Account account = TransactionServiceBuilder.createAccount();
    final OperationType operationType = TransactionServiceBuilder.createOperationType();

    PowerMockito.when(accountRepository.findById(any())).thenReturn(Optional.of(account));
    PowerMockito.when(operationTypeRepository.findById(any())).thenReturn(Optional.of(operationType));
    PowerMockito.when(accountRepository.saveAndFlush(any())).thenReturn(account);
    PowerMockito.when(transactionRepository.saveAndFlush(any()))
        .thenReturn(TransactionServiceBuilder.createTransaction(account, operationType, 100.0));

    assertThat(transactionService.create(TransactionServiceBuilder.createDTO())).isPresent();

  }

  @Test
  public void shouldThrowExceptionNoAvailableCreditLimit() {

    final Account account = TransactionServiceBuilder.createAccount();
    final OperationType operationType = TransactionServiceBuilder.createOperationType();

    operationType.setDescription(OperationTypeDescription.SAQUE);

    PowerMockito.when(accountRepository.findById(any())).thenReturn(Optional.of(account));
    PowerMockito.when(operationTypeRepository.findById(any())).thenReturn(Optional.of(operationType));

    final TransactionDTO transactionDTO = TransactionServiceBuilder.createDTO();

    transactionDTO.setAmount(2000.0);

    try {
      transactionService.create(transactionDTO);
      Assert.fail("Deveria ter lançado exception");
    } catch (Exception ex) {
      assertThat(ex).isInstanceOf(BadRequestException.class);
    }

  }

  @Test
  public void shouldThrowExceptionWhenDTOIsNull() {

    try {
      transactionService.create(null);
      Assert.fail("Deveria ter lançado exception");
    } catch (Exception ex) {
      assertThat(ex).isInstanceOf(BadRequestException.class);
    }

  }

  @Test
  public void shouldThrowExceptionWhenAmountEqualsZero() {

    final TransactionDTO transactionDTO = TransactionServiceBuilder.createDTO();

    transactionDTO.setAmount(0.0);

    try {
      transactionService.create(transactionDTO);
      Assert.fail("Deveria ter lançado exception");
    } catch (Exception ex) {
      assertThat(ex).isInstanceOf(BadRequestException.class);
    }

  }

  @Test
  public void shouldCreateTransactionWithWithdrawEnum() {

    final Account account = TransactionServiceBuilder.createAccount();
    final OperationType operationType = TransactionServiceBuilder.createOperationType();

    operationType.setDescription(OperationTypeDescription.SAQUE);

    PowerMockito.when(accountRepository.findById(any())).thenReturn(Optional.of(account));
    PowerMockito.when(operationTypeRepository.findById(any())).thenReturn(Optional.of(operationType));
    PowerMockito.when(accountRepository.saveAndFlush(any())).thenReturn(account);
    PowerMockito.when(transactionRepository.saveAndFlush(any()))
        .thenReturn(TransactionServiceBuilder.createTransaction(account, operationType, 100.0));

    final TransactionDTO transactionDTO = TransactionServiceBuilder.createDTO();

    transactionDTO.setAmount(100.0);

    assertThat(transactionService.create(TransactionServiceBuilder.createDTO())).isPresent();

  }

  @Test
  public void shouldBeEqualsThePaymentAmount() throws Exception {

    final Account account = TransactionServiceBuilder.createAccount();
    final TransactionDTO transactionDTO = TransactionServiceBuilder.createDTO();
    final OperationType operationType = TransactionServiceBuilder.createOperationType();

    WhiteboxImpl.invokeMethod(transactionService, "isPaymentTransaction", transactionDTO, account, operationType);

    Assert.assertEquals((Double) 2000.0, account.getAvailableCreditLimit());

  }

  @Test
  public void shouldBeEqualsTheWithdrawAmount() throws Exception {

    final Account account = TransactionServiceBuilder.createAccount();

    WhiteboxImpl.invokeMethod(transactionService, "doTransactionValue", account, 1000.0);

    Assert.assertEquals((Double) 1000.0, account.getAvailableCreditLimit());

  }

}
