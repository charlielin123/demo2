package com.cathaybk.demo.vo;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CurrencyVo extends BaseVo {

  private List<Map<String, String>> currency;

}
