package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.PlanDay;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class PlanDayDAO {
    private SqlSessionFactory sqlSessionFactory;

    public PlanDayDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createPlanDay(PlanDay planDay) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("PlanDay.insert", planDay);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updatePlanDay(PlanDay planDay) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("PlanDay.update", planDay);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteAllPlanDays(int planId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("PlanDay.deleteAll", planId);
            session.commit();
        } finally {
            session.close();
        }
    }
}
