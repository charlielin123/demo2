package com.cathaybk.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.cathaybk.demo.pojo.exchangeRate.GetByDateRangeReq;
import com.cathaybk.demo.vo.CurrencyVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
class APITest {

  @Autowired
  private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void wrongDateTest() {
    GetByDateRangeReq req = new GetByDateRangeReq();
    req.setStartDate("2023/01/01");
    req.setEndDate("2023/01/02");
    req.setCurrency("USD");
    assertDoesNotThrow(() -> {
      CurrencyVo vo = doApi(req);
      assertEquals("E0001", vo.getError().getCode());
      assertEquals("日期區間不符", vo.getError().getMessage());
    });
  }
  @Test
  void nomalTest() {
    GetByDateRangeReq req = new GetByDateRangeReq();
    req.setStartDate("2023/03/01");
    req.setEndDate("2024/01/09");
    req.setCurrency("USD");
    assertDoesNotThrow(() -> {
      CurrencyVo vo = doApi(req);
      assertEquals("0000", vo.getError().getCode());
      assertEquals("成功", vo.getError().getMessage());
    });
  }

  private CurrencyVo doApi(GetByDateRangeReq req) throws Exception {
    String reqJson;
    reqJson = objectMapper.writeValueAsString(req);
    MockHttpServletRequestBuilder requestBuilder = post("/currency/getCurrency").content(reqJson)
        .contentType("application/json;charset=UTF-8").accept("application/json;charset=UTF-8");

    MockHttpServletResponse res = mockMvc.perform(requestBuilder)
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn().getResponse();
    
    var vo=objectMapper.readValue(res.getContentAsString(), CurrencyVo.class);
    
    System.out.println("輸出結果為："+objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(vo));

    return vo;
  }

}
