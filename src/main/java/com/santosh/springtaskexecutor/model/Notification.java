package com.santosh.springtaskexecutor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification extends ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @Basic(optional = false)
    @Column(name = "recorded_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date recordedDate;

}
