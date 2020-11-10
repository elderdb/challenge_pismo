package com.pismo.challenge.domain;

import javax.persistence.*;

@Entity
@Table(name = "accounts", schema = "public")
public class Account {

  @Id
  @SequenceGenerator(name = "account_generator", sequenceName = "accounts_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "account_generator")
  private Long accountId;

  private Long documentNumber;

  private Double availableCreditLimit = 0.0;

  public static Account create(final Long documentNumber) {

    final Account account = new Account();

    account.documentNumber = documentNumber;

    return account;

  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(Long documentNumber) {
    this.documentNumber = documentNumber;
  }

  public Double getAvailableCreditLimit() {
    return availableCreditLimit;
  }

  public void setAvailableCreditLimit(Double availableCreditLimit) {
    this.availableCreditLimit = availableCreditLimit;
  }

  public Account from(final Account account) {
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Account{");
    sb.append("accountId=").append(accountId);
    sb.append(", documentNumber=").append(documentNumber);
    sb.append(", availableCreditLimit=").append(availableCreditLimit);
    sb.append('}');
    return sb.toString();
  }
}
