package com.cathaybk.demo.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestWrongTypeException extends RuntimeException {

  public RequestWrongTypeException(String message) {
    super(message);
  }

}
