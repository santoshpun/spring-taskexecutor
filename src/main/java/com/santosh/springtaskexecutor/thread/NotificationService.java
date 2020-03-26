package com.santosh.springtaskexecutor.thread;

import com.santosh.springtaskexecutor.dto.NotificationDTO;
import com.santosh.springtaskexecutor.model.NotificationList;
import com.santosh.springtaskexecutor.repository.NotificationDAO;
import com.santosh.springtaskexecutor.task.MessageSenderTask;
import com.santosh.springtaskexecutor.util.NotificationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    @Qualifier("threadPoolExecutor")
    private AsyncTaskExecutor taskExecutor;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private NotificationDAO notificationDAO;

    //initial delay is set to prepare data from the commandline runner
    @Scheduled(initialDelay = 25000, fixedDelay = 5000)
    public void execute() {
        try {
            log.info("========= Fetching new batch ================");
            log.info("Fixed delay iteration : " + new Date());
            List<Future<?>> futureList = new ArrayList<>();

            List<NotificationList> notificationList = notificationDAO.getNotifications();
            log.info("Size : " + notificationList.size());

            List<NotificationDTO> notificationDTOs = NotificationUtil.arrangeNotification(notificationList);

            for (NotificationDTO notificationDTO : notificationDTOs) {
                MessageSenderTask messageSenderTask = applicationContext.getBean(MessageSenderTask.class, notificationDTO);
                Future<?> future = taskExecutor.submit(messageSenderTask);
                futureList.add(future);
            }

            for (Future<?> future : futureList) {
                log.info("Message task COMPLETED : " + future.get());
            }
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }


//    @Scheduled(fixedDelay = 1000)
//    public void execute() {
//        List<Future<?>> futureList = new ArrayList<>();
//
//        //while (true) {
//            try {
//                log.info("Running next batch");
//
//                for (int i = 1; i <= 5; i++) {
//                    ConsolePrintingTask myThread = applicationContext.getBean(ConsolePrintingTask.class, i);
//                    Future<?> future = taskExecutor.submit(myThread);
//                    futureList.add(future);
//                }
//
//                for (Future<?> future : futureList) {
//                    System.out.println(future.get());
//                }
//
//                TimeUnit.SECONDS.sleep(2);
//            } catch (Exception e) {
//                log.error("Exception ", e);
//            }
//        //}
//    }
}
