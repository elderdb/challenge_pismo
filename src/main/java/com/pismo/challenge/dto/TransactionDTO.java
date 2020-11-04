package com.pismo.challenge.dto;

import javax.validation.constraints.NotNull;

public class TransactionDTO {

  @NotNull(message = "Object cannot be null")
  private Long accountId;

  private int operationTypeId;

  @NotNull(message = "Object cannot be null")
  private Double amount;

  public Long getAccountId() {
    return accountId;
  }

  public int getOperationTypeId() {
    return operationTypeId;
  }

  public Double getAmount() {
    return amount;
  }
}
