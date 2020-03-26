package com.santosh.springtaskexecutor.repository;

import com.santosh.springtaskexecutor.model.NotificationList;

import java.util.List;

public interface NotificationDAO {

    List<NotificationList> getNotifications();

    int update(long notificationId, long startId, long endId);

    void flush();
}
