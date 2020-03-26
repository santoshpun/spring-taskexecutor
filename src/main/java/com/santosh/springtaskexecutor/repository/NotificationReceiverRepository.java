package com.santosh.springtaskexecutor.repository;

import com.santosh.springtaskexecutor.model.NotificationReceiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationReceiverRepository extends JpaRepository<NotificationReceiver, Long> {
}
