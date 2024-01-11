# Demo

此專案有兩個branch 

分別使用不同排程工具

- main - 使用quartz

  路徑為
  
      src\main\java\com\cathaybk\demo\jobs\QuartzTask.java
- schedule - 使用spring 提供的@Scheduled

  路徑為

      src\main\java\com\cathaybk\demo\task\DemoTask.java

## 1.批次每日 18:00呼叫 API

此功能的unit test 位於 

    src\test\java\com\cathaybk\demo\UnitTest.java

## 2.forex API

此 post API uri 為

    /currency/getCurrency

並另有此API的單元測試，分別為正常狀況以及日期格式錯誤

路徑為

    src\test\java\com\cathaybk\demo\APITest.java

Method 分別為

- 成功：nomalTest

- 失敗： wrongDateTest
