package com.santosh.springtaskexecutor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "notification_receiver")
public class NotificationReceiver extends ModelBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int status;
    @Column(name = "receiver_email")
    private String receiverEmail;
    @Column(name = "processed_thread")
    private String processedThread;
    @Column(name = "msg_id")
    private String msgId;
    @Column(name = "notified_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date notifiedAt;
    @JoinColumn(name = "notification_id", referencedColumnName = "id")
    @ManyToOne()
    private Notification notification;
}
