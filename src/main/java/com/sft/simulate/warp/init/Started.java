package com.sft.simulate.warp.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2019-07-02.
 * @desc
 */
@Slf4j
@Component
public class Started implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("系统启动之后");
    }
}
