package com.cathaybk.demo.jobs;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.cathaybk.demo.service.ExchangeRateService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class QuartzTask {

  private final ExchangeRateService exchangeRateService;

  public void doBatch() {
    try {
      exchangeRateService.getDataAndSave();
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }
  }

}