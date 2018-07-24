package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.PlanDayActivity;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class PlanDayActivityDAO {
    private SqlSessionFactory sqlSessionFactory;

    public PlanDayActivityDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createPlanDayActivity(PlanDayActivity planDayActivity) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("PlanDayActivity.insert", planDayActivity);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteAllPlanDayActivities(int planId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("PlanDayActivity.deleteAll", planId);
            session.commit();
        } finally {
            session.close();
        }
    }
}
