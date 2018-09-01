package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.ComplianceMeal;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ComplianceMealDAO {
    private SqlSessionFactory sqlSessionFactory;

    public ComplianceMealDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createComplianceMeal(ComplianceMeal complianceMeal) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("ComplianceMeal.insert", complianceMeal);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updateComplianceMeal(ComplianceMeal complianceMeal) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("ComplianceMeal.update", complianceMeal);
            session.commit();
        } finally {
            session.close();
        }
    }
}
