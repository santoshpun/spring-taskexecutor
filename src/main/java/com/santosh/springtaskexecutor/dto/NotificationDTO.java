package com.santosh.springtaskexecutor.dto;

import com.santosh.springtaskexecutor.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NotificationDTO extends ModelBase {
    private Long notificationId;
    private String message;
    private List<ReceiverDTO> receivers;

    public void addReceiver(ReceiverDTO gcmReceiver) {
        if (receivers == null) {
            receivers = new ArrayList<>();
        }
        receivers.add(gcmReceiver);
    }
}
