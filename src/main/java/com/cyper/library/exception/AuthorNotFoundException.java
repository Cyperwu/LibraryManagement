package com.cyper.library.exception;

public class AuthorNotFoundException extends NotFoundException {
  public AuthorNotFoundException() {
    super("未找到该作者");
  }
}
