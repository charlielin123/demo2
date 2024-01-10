package com.cathaybk.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cathaybk.demo.pojo.exchangeRate.GetByDateRangeReq;
import com.cathaybk.demo.service.ExchangeRateService;
import com.cathaybk.demo.vo.CurrencyVo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class ExchangeRateController {
  private final ExchangeRateService exchangeRateService;

  @PostMapping("/getCurrency")
  public CurrencyVo testController(@RequestBody GetByDateRangeReq req)throws Exception{
    return exchangeRateService.getByDateRange(req);
  }
}
