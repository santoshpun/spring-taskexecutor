package com.santosh.springtaskexecutor.repository.impl;

import com.santosh.springtaskexecutor.model.NotificationList;
import com.santosh.springtaskexecutor.repository.NotificationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Repository
public class NotificationDAOImpl implements NotificationDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${total.notification.to.process}")
    private int toNotificationToProcess;

    @Override
    public List<NotificationList> getNotifications() {
        try {
            String sql = "select n.id as notificationId,n.message as message," +
                    " nr.id as receiverId,nr.receiver_email as receiverEmail " +
                    " from notification n join notification_receiver nr on(n.id=nr.notification_id and nr.status=0) " +
                    " order by nr.id asc limit "+toNotificationToProcess;

            return jdbcTemplate.query(sql, new NotificationRowMapper());
        } catch (Exception e) {
            log.error("Exception ", e);
            return null;
        }
    }

    @Override
    public int update(long notificationId, long startId, long endId) {
        String thread = Thread.currentThread().getName() + ":" + startId + ":" + endId;
        String sql = "update notification_receiver set status=1,notified_at=now(),processed_thread='" + thread + "' where notification_id=" + notificationId +
                " and id between " + startId + " and " + endId;

       // log.info(Thread.currentThread().getName() + " : update query : " + sql);
        int affected = jdbcTemplate.update(sql);
        return affected;
    }

    @Override
    public void flush() {
        String sql = "truncate table notification_receiver";
        jdbcTemplate.update(sql);

        jdbcTemplate.update("delete from notification");

        jdbcTemplate.update("alter table notification AUTO_INCREMENT = 1;");
    }


    class NotificationRowMapper implements RowMapper<NotificationList> {
        @Override
        public NotificationList mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificationList notificationList = new NotificationList();
            notificationList.setNotificationId(rs.getLong("notificationId"));
            notificationList.setMessage(rs.getString("message"));
            notificationList.setReceiverId(rs.getLong("receiverId"));
            notificationList.setReceiverEmail(rs.getString("receiverEmail"));
            return notificationList;
        }
    }
}
