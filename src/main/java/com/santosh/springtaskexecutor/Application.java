package com.santosh.springtaskexecutor;

import com.santosh.springtaskexecutor.model.Notification;
import com.santosh.springtaskexecutor.model.NotificationReceiver;
import com.santosh.springtaskexecutor.repository.NotificationDAO;
import com.santosh.springtaskexecutor.repository.NotificationReceiverRepository;
import com.santosh.springtaskexecutor.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationReceiverRepository notificationReceiverRepository;
    @Autowired
    private NotificationDAO notificationDAO;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        notificationDAO.flush();

//        for (int i = 1; i <= 5; i++) {
//            Notification notification = new Notification();
//            notification.setMessage("message" + i);
//            notification.setRecordedDate(new Date());
//
//            notificationRepository.save(notification);
//
//            int count = i <= 4 ? 1 : 5000;
//            for (int n = 1; n <= count; n++) {
//                NotificationReceiver notificationReceiver = new NotificationReceiver();
//                notificationReceiver.setNotification(notification);
//                notificationReceiver.setStatus(0);
//                notificationReceiver.setReceiverEmail("santosh" + n + "@gmail.com");
//                notificationReceiverRepository.save(notificationReceiver);
//            }
//        }

        for (int i = 1; i <= 1; i++) {
            Notification notification = new Notification();
            notification.setMessage("message" + i);
            notification.setRecordedDate(new Date());

            notificationRepository.save(notification);

            int count = 5000;
            for (int n = 1; n <= count; n++) {
                NotificationReceiver notificationReceiver = new NotificationReceiver();
                notificationReceiver.setNotification(notification);
                notificationReceiver.setStatus(0);
                notificationReceiver.setReceiverEmail("santosh" + n + "@gmail.com");
                notificationReceiverRepository.save(notificationReceiver);
            }
        }
    }

}
