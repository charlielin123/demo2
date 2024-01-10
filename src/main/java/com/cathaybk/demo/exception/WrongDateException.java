package com.cathaybk.demo.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WrongDateException extends RuntimeException {
  public WrongDateException(String message) {
    super(message);
  }

}
