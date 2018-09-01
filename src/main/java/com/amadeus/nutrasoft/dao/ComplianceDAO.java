package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Compliance;
import com.amadeus.nutrasoft.model.Plan;
import com.amadeus.nutrasoft.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplianceDAO {
    private SqlSessionFactory sqlSessionFactory;

    public ComplianceDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createCompliance(Compliance compliance) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("Compliance.insert", compliance);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updateCompliance(Compliance compliance) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("Compliance.update", compliance);
            session.commit();
        } finally {
            session.close();
        }
    }

    public Compliance getComplianceById(int id, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("lang", lang);

        try {
            return session.selectOne("Compliance.selectById", map);
        } finally {
            session.close();
        }
    }

    /**
     * Retorna el plan con su lista de compliance.
     *
     * @param clientId
     * @param lang
     * @return
     */
    public Plan getPlanByClientId(int clientId, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("clientId", clientId);
        map.put("lang", lang);

        try {
            return session.selectOne("Compliance.selectByClientId", map);
        } finally {
            session.close();
        }
    }

    /**
     * Retorna el reporte de cumplimiento.
     *
     * @param coachId
     * @return
     */
    public List<User> getClientsByCoachId(int coachId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Compliance.selectByCoachId", coachId);
        } finally {
            session.close();
        }
    }


    public int getCountByPlanId(int planId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectOne("Compliance.selectCount", planId);
        } finally {
            session.close();
        }
    }
}
