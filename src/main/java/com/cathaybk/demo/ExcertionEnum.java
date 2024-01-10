package com.cathaybk.demo;

import com.cathaybk.demo.exception.RequestWrongTypeException;
import com.cathaybk.demo.exception.RequsetMissingParemException;
import com.cathaybk.demo.exception.WrongDateException;

import lombok.Getter;

@Getter
public enum ExcertionEnum {
  E0001("E0001", "日期區間不符", WrongDateException.class),
  E0002("E0002", "請求缺少必要參數", RequsetMissingParemException.class),
  E0003("E0003", "參數格式錯誤，請檢查", RequestWrongTypeException.class),
  ;

  private String message;
  private Class<? extends Throwable> exception;
  private String errorCode;

  ExcertionEnum(String errorCode, String message, Class<? extends Throwable> exception) {
    this.message = message;
    this.exception = exception;
    this.errorCode = errorCode;
  }

}
