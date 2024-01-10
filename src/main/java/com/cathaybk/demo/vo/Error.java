package com.cathaybk.demo.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error implements Serializable {
  private String code = "0000";
  private String message = "成功";
}