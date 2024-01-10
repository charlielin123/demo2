package com.cathaybk.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cathaybk.demo.service.ExchangeRateService;

@SpringBootTest
public class UnitTest {

  @Autowired
  private ExchangeRateService exchangeRateService;

  @Test
  public void test() throws Exception {
    exchangeRateService.getDataAndSave();
  }
  }