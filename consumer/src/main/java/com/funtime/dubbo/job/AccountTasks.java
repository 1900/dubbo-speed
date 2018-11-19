package com.funtime.dubbo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by steven on 2018/9/30.
 */
@Component
public class AccountTasks {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 一分钟执行一次
    @Scheduled(cron = "0 0/1 * * * ?")
    public void autoPassTask() {
        logger.info("applyTask()-->{}");
    }

}
