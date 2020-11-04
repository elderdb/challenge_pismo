package com.pismo.challenge.utils.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(final String message) {
    super(message);
  }
}
