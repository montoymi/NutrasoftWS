package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Notification;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class NotificationDAO {
    private SqlSessionFactory sqlSessionFactory;

    public NotificationDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createNotification(Notification notification) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("Notification.insert", notification);
            session.commit();
        } finally {
            session.close();
        }
    }

    public List<Notification> getNotificationsByUserId(int userId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Notification.selectByUserId", userId);
        } finally {
            session.close();
        }
    }
}
