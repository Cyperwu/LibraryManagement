package com.cyper.library.exception;

public class BookNotFoundException extends NotFoundException {
  public BookNotFoundException() {
    super("未找到该书籍");
  }
}
