package com.santosh.springtaskexecutor.dto;

import com.santosh.springtaskexecutor.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiverDTO extends ModelBase {
    private String email;
    private Long receiverId;

}
