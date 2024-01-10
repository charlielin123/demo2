package com.cathaybk.demo.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cathaybk.demo.model.ExchangeRate;

@Repository
public interface ExchangeRateDAO extends JpaRepository<ExchangeRate, Long> {

  /**
   * 查找指定日期區間內的兌台幣匯率
   * @param startDate
   * @param endDate
   * @param currencyFrom
   * @return
   */
  @Query("select e from ExchangeRate e where e.date between ?1 and ?2 and e.currencyFrom = ?3 and e.currencyTo = 'NTD'")
  List<ExchangeRate> findByDateBetweenAndCurrencyFrom(Date startDate, Date endDate, String currencyFrom);
}
