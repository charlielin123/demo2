package com.cathaybk.demo.model.keys;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRateKey implements Serializable {

  private Date date;

  private String currencyFrom;

  private String currencyTo;

}
