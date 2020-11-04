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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Account{");
    sb.append("accountId=").append(accountId);
    sb.append(", documentNumber=").append(documentNumber);
    sb.append('}');
    return sb.toString();
  }

}
