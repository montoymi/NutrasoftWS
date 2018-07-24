package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Plan;
import com.amadeus.nutrasoft.model.PlanDay;
import com.amadeus.nutrasoft.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanDAO {
    private SqlSessionFactory sqlSessionFactory;

    public PlanDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createPlan(Plan plan) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("Plan.insert", plan);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updatePlan(Plan plan) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("Plan.update", plan);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deletePlan(int id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("Plan.delete", id);
            session.commit();
        } finally {
            session.close();
        }
    }

    public Plan getPlanById(int id, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("lang", lang);

        try {
            Plan plan = session.selectOne("Plan.selectById", map);
            setPlanToPlanDays(plan);
            return plan;
        } finally {
            session.close();
        }
    }

    public Plan getPlanByClientId(int clientId, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            // Este query no tiene la información completa del plan.
            Plan plan = session.selectOne("Plan.selectByClientId", clientId);
            if (plan == null) {
                return null;
            }
            // Retorna la información completa del plan.
            return getPlanById(plan.getId(), lang);
        } finally {
            session.close();
        }
    }

    public List<User> getClientsByCoachId(int coachId, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("coachId", coachId);
        map.put("lang", lang);

        try {
            return session.selectList("Plan.selectByCoachId", map);
        } finally {
            session.close();
        }
    }

    private void setPlanToPlanDays(Plan plan) {
        for (PlanDay planDay : plan.getPlanDayList()) {
            planDay.setPlan(plan);
        }
    }
}
