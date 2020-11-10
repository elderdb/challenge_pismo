package com.pismo.challenge.enums;

public enum ErrorMessageEnum {

  DOCUMENT_NUMBER_IS_NULL("001 - Document number can not be null."),
  DOCUMENT_NUMBER_ALREADY_IN_USE("002 - Document number is already in use."),
  PAYLOAD_CANNOT_BE_NULL("003 - Payload can not be null."),
  ACCOUNT_NOT_FOUND("004 - Account not found."),
  OPERATION_TYPE_IS_INVALID("005 - Operation Type is invalid."),
  AMOUNT_MUST_BE_GREATER_THAN_ZERO("006 - Amount must be greater than zero."),
  INPUT_OBJECT_ERROR_PARSE("007 - Can not parse payload"),
  NO_AVAILABLE_CREDIT_LIMIT("008 - No available credit limit");

  private final String message;

  ErrorMessageEnum(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
