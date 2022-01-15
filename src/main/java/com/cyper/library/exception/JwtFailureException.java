package com.cyper.library.exception;

public class JwtFailureException extends RuntimeException {
  public JwtFailureException(String token) {
    super("无效token: " + token);
  }
}
