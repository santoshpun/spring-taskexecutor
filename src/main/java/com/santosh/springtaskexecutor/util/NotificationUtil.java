package com.santosh.springtaskexecutor.util;

import com.santosh.springtaskexecutor.dto.NotificationDTO;
import com.santosh.springtaskexecutor.dto.ReceiverDTO;
import com.santosh.springtaskexecutor.model.NotificationList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class NotificationUtil {

    public static List<NotificationDTO> arrangeNotification(List<NotificationList> notifiers) {
        LinkedHashMap<Long, NotificationDTO> notificationMap = new LinkedHashMap<>();

        for (NotificationList notifier : notifiers) {
            Long notificationId = notifier.getNotificationId();

            if (!notificationMap.containsKey(notificationId)) {
                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setNotificationId(notifier.getNotificationId());
                notificationDTO.setMessage(notifier.getMessage());

                ReceiverDTO receiver = new ReceiverDTO();
                receiver.setReceiverId(notifier.getReceiverId());
                receiver.setEmail(notifier.getReceiverEmail());

                notificationDTO.addReceiver(receiver);

                notificationMap.put(notificationId, notificationDTO);
            } else {
                NotificationDTO notificationDTO = notificationMap.get(notificationId);

                ReceiverDTO receiver = new ReceiverDTO();
                receiver.setReceiverId(notifier.getReceiverId());
                receiver.setEmail(notifier.getReceiverEmail());

                notificationDTO.addReceiver(receiver);
            }
        }
        return new ArrayList<>(notificationMap.values());
    }


}
