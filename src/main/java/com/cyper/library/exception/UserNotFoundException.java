package com.cyper.library.exception;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    super("未找到该用户");
  }
}
