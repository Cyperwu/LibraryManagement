package com.cyper.library.exception;

public class NotFoundException extends ServiceFailureException {
  public NotFoundException(String message) {
    super(message, 404);
  }
}
