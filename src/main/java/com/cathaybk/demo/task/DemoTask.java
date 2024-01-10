package com.cathaybk.demo.task;

import org.springframework.scheduling.annotation.Scheduled;

import com.cathaybk.demo.service.ExchangeRateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DemoTask {

  private final ExchangeRateService exchangeRateService;

  @Scheduled( /** 每天18:00一次 */
      cron = "0 0 18 * * ?")
  public void run() {
    exchangeRateService.getDataAndSave();
  }
}
