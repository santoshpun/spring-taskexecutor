package com.santosh.springtaskexecutor.task;

import com.santosh.springtaskexecutor.dto.NotificationDTO;
import com.santosh.springtaskexecutor.dto.ReceiverDTO;
import com.santosh.springtaskexecutor.util.Partition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MessageSenderTask implements Callable<String> {
    @Autowired
    @Qualifier("fcmThreadPool")
    private AsyncTaskExecutor taskExecutor;
    @Autowired
    private ApplicationContext applicationContext;
    private NotificationDTO notificationDTO;

    public MessageSenderTask(NotificationDTO notificationDTO) {
        this.notificationDTO = notificationDTO;
    }

    @Override
    public String call() throws Exception {
        log.info(Thread.currentThread().getName()+ " : Processing notification id : " + notificationDTO.getNotificationId());

        List<Future<?>> futureList = new ArrayList<>();

        log.info(Thread.currentThread().getName() + "**********************");
        log.info(Thread.currentThread().getName() + " : Notification id : " + notificationDTO.getNotificationId());
        log.info(Thread.currentThread().getName() + " : Message : " + notificationDTO.getMessage());
        log.info(Thread.currentThread().getName() + " : Receiver size : " + notificationDTO.getReceivers().size());

        TimeUnit.MILLISECONDS.sleep(5);

        List<ReceiverDTO> receivers = notificationDTO.getReceivers();

        Partition<ReceiverDTO> chunks = Partition.ofSize(receivers, 10);
        log.info(Thread.currentThread().getName()+ " : Total chunks : " + chunks.size());

        for (List<ReceiverDTO> receiverList : chunks) {
            EmailSenderTask emailSenderTask = applicationContext.getBean(EmailSenderTask.class, notificationDTO.getNotificationId(), receiverList);
            Future<?> future = taskExecutor.submit(emailSenderTask);
            futureList.add(future);
        }

        for (Future<?> future : futureList) {
            log.info(Thread.currentThread().getName()+ " : Email Task COMPLETED : " + future.get());
        }

        String logMsg = Thread.currentThread().getName()+ " : Message processing completed for notification id : " + notificationDTO.getNotificationId();

        return logMsg;
    }
}
