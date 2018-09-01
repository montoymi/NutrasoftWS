package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Activity;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ActivityDAO {
    private SqlSessionFactory sqlSessionFactory;

    public ActivityDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Activity> getAllActivities(String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Activity.selectAll", lang);
        } finally {
            session.close();
        }
    }

    public List<Activity> getDefaultActivities(String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Activity.selectDefault", lang);
        } finally {
            session.close();
        }
    }
}
