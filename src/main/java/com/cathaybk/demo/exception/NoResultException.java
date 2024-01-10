package com.cathaybk.demo.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoResultException extends RuntimeException {

  public NoResultException(String message) {
    super(message);
  }
}
