package com.cathaybk.demo.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.cathaybk.demo.jobs.QuartzTask;

@Configuration
public class QuartzConfigration {

  @Bean(name = "scheduler")
  SchedulerFactoryBean schedulerFactory(Trigger... triggers) {
    SchedulerFactoryBean bean = new SchedulerFactoryBean();
    bean.setOverwriteExistingJobs(true);
    bean.setStartupDelay(1);
    bean.setTriggers(triggers);
    return bean;
  }

  @Bean(name = "getCurrencyAndSave") 
  MethodInvokingJobDetailFactoryBean jobDetail(QuartzTask task) {
    MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
    jobDetail.setConcurrent(false);
    jobDetail.setTargetObject(task);
    jobDetail.setTargetMethod("doBatch"); 
    return jobDetail;
  }

  @Bean
  CronTriggerFactoryBean jobTrigger(JobDetail getCurrencyAndSave) {
    // 每天 18:00執行
    String cron = "0 0 18 * * ?";
    CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
    trigger.setJobDetail(getCurrencyAndSave); 
    trigger.setCronExpression(cron);
    return trigger;
  }

}