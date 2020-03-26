package com.santosh.springtaskexecutor.task;

import com.santosh.springtaskexecutor.dto.ReceiverDTO;
import com.santosh.springtaskexecutor.repository.NotificationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmailSenderTask implements Callable<String> {
    @Autowired
    private NotificationDAO notificationDAO;
    private Long notificationId;
    private List<ReceiverDTO> receivers;

    public EmailSenderTask(Long notificationId, List<ReceiverDTO> receivers) {
        this.notificationId = notificationId;
        this.receivers = receivers;
    }

    @Override
    public String call() throws Exception {
        log.info(Thread.currentThread().getName() + " : Processing email sender task");
        long startId = receivers.get(0).getReceiverId();
        long endId = receivers.get(receivers.size() - 1).getReceiverId();
        log.info(Thread.currentThread().getName() + " : start id : " + startId + ", end id : " + endId);

        TimeUnit.MILLISECONDS.sleep(500);

        //creating delay explicitly to check thread waiting
        //TimeUnit.SECONDS.sleep(60);

        notificationDAO.update(notificationId, startId, endId);
        String message = "Completed processing for receiver id between : " + startId + " and " + endId;

        return message;
    }
}
