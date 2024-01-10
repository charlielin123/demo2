package com.cathaybk.demo.config;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cathaybk.demo.ExcertionEnum;
import com.cathaybk.demo.vo.BaseVo;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalRestExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseVo> handleAllException(Exception ex) {

    log.error(ex.getMessage(), ex);
    BaseVo vo = new BaseVo();
    String code = "E9999";
    String message = "Error";
    vo.getError().setMessage(message);

    for (var c : ExcertionEnum.values()) {
      if (ex.getClass() == c.getException()) {
        code=c.getErrorCode();
        message = Objects.isNull(c.getMessage()) ? message : c.getMessage();
      }
    }

    message = Objects.isNull(ex.getMessage()) ? message : ex.getMessage();

    vo.getError().setCode(code);
    vo.getError().setMessage(message);
    return ResponseEntity.badRequest().body(vo);
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<BaseVo> handleUnknowException(Exception ex) {

    log.error(ex.getMessage(), ex);
    BaseVo vo = new BaseVo();
    vo.getError().setCode("EXXXX");
    vo.getError().setMessage("請聯繫系統管理員");
    return ResponseEntity.badRequest().body(vo);
  }

}