package com.cathaybk.demo.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequsetMissingParemException extends RuntimeException {

  public RequsetMissingParemException(String message) {
    super(message);
  }
}
