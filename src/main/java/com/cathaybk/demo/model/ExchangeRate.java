package com.cathaybk.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.cathaybk.demo.model.keys.ExchangeRateKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_rate")
@IdClass(ExchangeRateKey.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate implements Serializable {
  @Id
  @Column(name = "Date")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date date;

  @Id
  @Column(name = "Currency_from")
  private String currencyFrom;

  @Id
  @Column(name = "Currency_to")
  private String currencyTo;

  @Id
  @Column(name = "Rate")
  private Double rate;
}
