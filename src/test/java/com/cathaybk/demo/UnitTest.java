package com.cathaybk.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cathaybk.demo.service.ExchangeRateService;

@SpringBootTest
class UnitTest {

  @Autowired
  private ExchangeRateService exchangeRateService;

  @Test
  void test() throws Exception {
    assertDoesNotThrow(()->{
      exchangeRateService.getDataAndSave();
    });
  }
}