package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.ComplianceDAO;
import com.amadeus.nutrasoft.dao.ComplianceMealDAO;
import com.amadeus.nutrasoft.dao.MealDAO;
import com.amadeus.nutrasoft.dao.PlanDAO;
import com.amadeus.nutrasoft.exception.ApplicationException;
import com.amadeus.nutrasoft.model.*;

import java.util.List;

import static com.amadeus.nutrasoft.exception.ApplicationException.PLAN_NOT_FOUND;

public class ComplianceService {
    private ComplianceDAO complianceDAO = new ComplianceDAO(MyBatisSqlSession.getSqlSessionFactory());
    private ComplianceMealDAO complianceMealDAO = new ComplianceMealDAO(MyBatisSqlSession.getSqlSessionFactory());
    private MealDAO mealDAO = new MealDAO(MyBatisSqlSession.getSqlSessionFactory());
    private PlanDAO planDAO = new PlanDAO(MyBatisSqlSession.getSqlSessionFactory());

    public void createCompliance(Compliance compliance) {
        complianceDAO.createCompliance(compliance);

        for (ComplianceMeal complianceMeal : compliance.getComplianceMealList()) {
            complianceMeal.setCompliance(compliance);
            complianceMealDAO.createComplianceMeal(complianceMeal);
        }
    }

    public void updateCompliance(Compliance compliance) {
        complianceDAO.updateCompliance(compliance);

        for (ComplianceMeal complianceMeal : compliance.getComplianceMealList()) {
            complianceMeal.setCompliance(compliance);
            complianceMealDAO.updateComplianceMeal(complianceMeal);
        }
    }

    public Compliance getComplianceById(int id, String lang) {
        return complianceDAO.getComplianceById(id, lang);
    }

    public Plan getPlanByClientId(int clientId, String lang) {
        Plan plan = complianceDAO.getPlanByClientId(clientId, lang);
        if (plan == null) {
            throw new ApplicationException(PLAN_NOT_FOUND);
        }

        return plan;
    }

    public List<User> getClientsByCoachId(int coachId) {
        return complianceDAO.getClientsByCoachId(coachId);
    }

    public List<Meal> getMealsByClientId(int clientId, String lang) {
        return mealDAO.getMealsByClientId(clientId, lang);
    }
}
