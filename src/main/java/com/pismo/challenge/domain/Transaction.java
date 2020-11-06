package com.pismo.challenge.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pismo.challenge.utils.date.CustomDateDeserializer;
import com.pismo.challenge.utils.date.CustomDateSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "transactions", schema = "public")
public class Transaction {

  @Id
  @SequenceGenerator(name = "transaction_generator", sequenceName = "transaction_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "transaction_generator")
  private Long transactionId;

  @JsonSerialize(using = CustomDateSerializer.class)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
  private final DateTime eventDate = DateTime.now();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "operation_type_id")
  private OperationType operationType;

  private Double amount;

  public static Transaction create(final Account account, final OperationType operationType, final Double amount) {

    final Transaction transaction = new Transaction();

    transaction.setAccount(account);
    transaction.setAmount(amount);
    transaction.setOperationType(operationType);

    return transaction;

  }

  public Long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public OperationType getOperationType() {
    return operationType;
  }

  public void setOperationType(OperationType operationType) {
    this.operationType = operationType;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public DateTime getEventDate() {
    return eventDate;
  }

}
