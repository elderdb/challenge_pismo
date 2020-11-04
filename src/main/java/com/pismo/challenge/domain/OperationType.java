package com.pismo.challenge.domain;

import com.pismo.challenge.enums.OperationTypeDescription;

import javax.persistence.*;

@Entity
@Table(name = "operations_types", schema = "public")
public class OperationType {

  @Id
  private int operationTypeId;

  @Enumerated(value = EnumType.STRING)
  private OperationTypeDescription description;

  public int getOperationTypeId() {
    return operationTypeId;
  }

  public void setOperationTypeId(int operationTypeId) {
    this.operationTypeId = operationTypeId;
  }

  public OperationTypeDescription getDescription() {
    return description;
  }

  public void setDescription(OperationTypeDescription description) {
    this.description = description;
  }
}
