package com.cathaybk.demo.pojo.exchangeRate;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByDateRangeReq implements Serializable {
  private String startDate;

  private String endDate;

  private String currency;
}
