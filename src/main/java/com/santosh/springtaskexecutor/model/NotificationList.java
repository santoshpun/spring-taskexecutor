package com.santosh.springtaskexecutor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationList extends ModelBase {
    private Long notificationId;
    private String message;
    private Long receiverId;
    private String receiverEmail;
}
