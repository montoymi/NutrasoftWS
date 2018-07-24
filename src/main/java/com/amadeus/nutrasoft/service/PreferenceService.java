package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.dao.ExcludedFoodDAO;
import com.amadeus.nutrasoft.dao.PreferenceDAO;
import com.amadeus.nutrasoft.model.ExcludedFood;
import com.amadeus.nutrasoft.model.Preference;
import com.amadeus.nutrasoft.mybatis.MyBatisSqlSession;

public class PreferenceService {
    private PreferenceDAO preferenceDAO = new PreferenceDAO(MyBatisSqlSession.getSqlSessionFactory());
    private ExcludedFoodDAO excludedFoodDAO = new ExcludedFoodDAO(MyBatisSqlSession.getSqlSessionFactory());

    public void updatePreference(Preference preference) {
        // Se elimina y luego se crea porque esta actualización es más compleja.
        excludedFoodDAO.deleteAllExcludedFoods(preference.getUser().getId());

        preferenceDAO.updatePreference(preference);

        for (ExcludedFood excludedFood : preference.getExcludedFoodList()) {
            excludedFood.setPreference(preference);
            excludedFoodDAO.createExcludedFood(excludedFood);
        }
    }

    public Preference getPreferenceByUserId(int userId, String lang) {
        return preferenceDAO.getPreferenceByUserId(userId, lang);
    }
}
