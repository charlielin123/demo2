package com.cathaybk.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.cathaybk.demo.dao.ExchangeRateDAO;
import com.cathaybk.demo.exception.NoResultException;
import com.cathaybk.demo.exception.RequestWrongTypeException;
import com.cathaybk.demo.exception.RequsetMissingParemException;
import com.cathaybk.demo.exception.WrongDateException;
import com.cathaybk.demo.model.ExchangeRate;
import com.cathaybk.demo.pojo.exchangeRate.GetByDateRangeReq;
import com.cathaybk.demo.vo.CurrencyVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateService {
  private final ExchangeRateDAO exchangeRateDAO;

  private static final String GET_CURRENCY_URL = "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates";

  private final RestTemplate restTemplate = new RestTemplate();
  private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
  private final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

  @Scheduled( /** 每天18:00一次 */
      cron = "0 0 18 * * ?")
  public void getDataAndSave() {
    var res = restTemplate.getForEntity(GET_CURRENCY_URL, String.class);
    String body = res.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    List<Map<String, String>> dtoList;
    try {
      String today = sdf2.format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
      dtoList = objectMapper.readValue(body, new TypeReference<List<Map<String, String>>>() {
      });
      dtoList = dtoList.stream().filter(dto -> dto.get("Date").equals(today)).collect(Collectors.toList());
      List<ExchangeRate> currencyDataList = new ArrayList<>();
      dtoList.forEach(data -> {
        currencyDataList.addAll(convert(data));
      });
      exchangeRateDAO.saveAll(currencyDataList);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }
  }

  public CurrencyVo getByDateRange(GetByDateRangeReq req) throws Exception {
    CurrencyVo vo = new CurrencyVo();
    try {
      checkReq(req);
      Date startDate = sdf.parse(req.getStartDate());
      Date endDate = sdf.parse(req.getEndDate());
      String currency = req.getCurrency().toUpperCase();

      List<ExchangeRate> list = exchangeRateDAO.findByDateBetweenAndCurrencyFrom(startDate, endDate,
          currency);

      List<Map<String, String>> resultList = list.stream().map(i -> {
        Map<String, String> map = new HashMap<>();
        i.getCurrencyFrom();
        map.put("date", sdf2.format(i.getDate()));
        map.put(i.getCurrencyFrom(), i.getRate().toString());
        return map;
      }).collect(Collectors.toList());
      if (resultList.isEmpty()) {
        throw new NoResultException();
      }
      vo.setCurrency(resultList);
    } catch (ParseException e) {
      log.error(e.getMessage(), e);
      throw new RequestWrongTypeException();
    }
    return vo;
  }

  private void checkReq(GetByDateRangeReq req) throws WrongDateException, RequsetMissingParemException {
    String regex = "^[\\d]{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][\\d]|3[0-1])$";
    if (!StringUtils.hasText(req.getStartDate()) || !StringUtils.hasText(req.getEndDate())
        || !StringUtils.hasText(req.getCurrency())) {
      throw new RequsetMissingParemException();
    }
    if (!req.getStartDate().matches(regex) || !req.getEndDate().matches(regex)) {
      throw new RequestWrongTypeException();
    }
    checkDateRange(req.getStartDate(), req.getEndDate());

  }

  /**
   * 檢查日期區間
   * 日期區間 僅限 1年前 ~當下日期 -1天
   * 
   * @return
   */
  private void checkDateRange(String startDate, String endDate) throws RuntimeException {
    try {

      Date starD = sdf.parse(startDate);
      Date endD = sdf.parse(endDate);
      Date today = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(today);
      calendar.set(calendar.get(Calendar.YEAR) - 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
      Date beforeOneYear = calendar.getTime();
      calendar.setTime(today);
      calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1);
      Date yesterday = calendar.getTime();
      if (starD.before(beforeOneYear) || endD.after(yesterday) || starD.after(endD)) {
        throw new WrongDateException();
      }
    } catch (ParseException e) {
      log.error(e.getMessage(), e);
      throw new RequestWrongTypeException(e.getMessage());
    }
  }

  private List<ExchangeRate> convert(Map<String, String> map) {

    List<ExchangeRate> list = new ArrayList<>();
    Date date = null;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      ExchangeRate exchangeRate = new ExchangeRate();
      String key = entry.getKey();
      String value = entry.getValue();
      if (key.equals("Date")) {
        try {
          date = sdf2.parse(value);
          exchangeRate.setDate(date);
        } catch (ParseException e) {
          log.error(e.getMessage(), e);
        }
      } else {
        String[] currency = key.split("/");

        exchangeRate.setDate(date);
        exchangeRate.setCurrencyFrom(currency[0]);
        exchangeRate.setCurrencyTo(currency[1]);
        exchangeRate.setRate(Double.parseDouble(value));
        list.add(exchangeRate);
      }
    }

    return list;

  }

}
