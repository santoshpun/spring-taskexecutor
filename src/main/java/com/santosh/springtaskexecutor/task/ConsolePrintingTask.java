package com.santosh.springtaskexecutor.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConsolePrintingTask implements Callable<String> {
    private int id;

    public ConsolePrintingTask(int id) {
        this.id = id;
    }

    @Override
    public String call() {
        log.info("Id : " + id);
        int taskId = new Random().nextInt(1000);
        log.info(Thread.currentThread().getName() + " : Console printing task run " + taskId);

        try {
            if (id % 2 == 0) {
                TimeUnit.SECONDS.sleep(5);
            } else {
                TimeUnit.SECONDS.sleep(20);
            }

        } catch (Exception e) {
            log.error("Exception ", e);
        }
        log.info(Thread.currentThread().getName() + " : Console printing task done >>>>>" + taskId);
        return "";
    }
}
