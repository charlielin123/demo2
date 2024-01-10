package com.cathaybk.demo.jobs;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.cathaybk.demo.service.ExchangeRateService;

import lombok.RequiredArgsConstructor;

@Configuration
@Component
@EnableScheduling
@RequiredArgsConstructor
public class QuartzTask {

    private final ExchangeRateService exchangeRateService;

    public void doBatch() {
        exchangeRateService.getDataAndSave();
    }
    
}