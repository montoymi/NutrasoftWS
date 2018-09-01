package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Notification;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;

public class NotificationTempDAO {
    private SqlSessionFactory sqlSessionFactory;

    public NotificationTempDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Notification getNotificationTempById(int id, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("lang", lang);

        try {
            return session.selectOne("NotificationTemp.selectById", map);
        } finally {
            session.close();
        }
    }
}
