package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.calc.DietSolver;
import com.amadeus.nutrasoft.dao.*;
import com.amadeus.nutrasoft.exception.ApplicationException;
import com.amadeus.nutrasoft.model.*;
import com.amadeus.nutrasoft.mybatis.MyBatisSqlSession;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amadeus.nutrasoft.commons.Utils.round;
import static com.amadeus.nutrasoft.exception.ApplicationException.DIET_MENU_NOT_FOUND;
import static com.amadeus.nutrasoft.exception.ApplicationException.DIET_PLAN_NOT_FOUND;

public class DietService {
    private static final Logger LOGGER = Logger.getLogger(DietService.class.getName());

    private PlanDAO planDAO = new PlanDAO(MyBatisSqlSession.getSqlSessionFactory());
    private PreferenceDAO preferenceDAO = new PreferenceDAO(MyBatisSqlSession.getSqlSessionFactory());
    private DietDAO dietDAO = new DietDAO(MyBatisSqlSession.getSqlSessionFactory());
    private WeightDAO weightDAO = new WeightDAO(MyBatisSqlSession.getSqlSessionFactory());
    private DietTypeDAO dietTypeDAO = new DietTypeDAO(MyBatisSqlSession.getSqlSessionFactory());

    /**
     * Genera la dieta en base al plan nutricional del cliente, según el menú del día
     * y semana dado.
     * La dieta no se guarda en ninguna tabla y se genera al momento de la consulta.
     * Las ventajas son:
     * 1. Es dinámica y puede ajustarse según los cambios de planes, por ejemplo si se
     * deja de entrenar en ese día.
     * 2. El plan puede editarse sin afectar la dieta, ya que no se guarda en la base
     * de datos.
     * Se mostrará en pantalla el menú del día solicitado. Para una relación de los
     * alimentos del menú de la semana se debera consultar el objeto menu en la base de
     * datos.
     *
     * @param clientId id del cliente
     * @param week     semana del menú.
     * @param day      día del menu.
     * @param lang     código del idioma.
     * @return
     */
    public Menu generateDiet(int clientId, byte week, byte day, String lang) {
        Menu menu = new Menu();

        // Obtiene el plan actual del cliente para completar
        // los parámetros de la consulta.
        Plan plan = planDAO.getPlanByClientId(clientId, lang);
        if (plan == null) {
            throw new ApplicationException(DIET_PLAN_NOT_FOUND);
        }

        // Obtiene las preferencias del cliente.
        Preference preference = preferenceDAO.getPreferenceByUserId(clientId, lang);

        menu.setCoach(plan.getClient().getCoach());
        menu.setDietType(preference.getDietType());
        menu.setMealCount(preference.getMealCount());
        menu.setWeek(week);
        menu.setDay(day);
        menu.setLang(lang);

        // Obtiene la información del menú del día.
        menu = dietDAO.getDietByParams(menu);
        if (menu == null) {
            LOGGER.log(Level.SEVERE, "[" + DIET_MENU_NOT_FOUND + "] clientId: {0}, week: {1}, day: {2}", new Object[]{clientId, week, day});
            throw new ApplicationException(DIET_MENU_NOT_FOUND);
        }

        /*
         * Calcula los pesos de los alimentos.
         */

        PlanDay planDay = getPlanDay(plan, day);

        for (MenuMeal menuMeal : menu.getMenuMealList()) {
            Dish dish = menuMeal.getDish();

            try {
                DietSolver.solve(planDay, preference, dish);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        menu.setPlanDay(planDay);
        return menu;
    }

    public List<Weight> getWeightsByNdbno(String ndbno, float weight, String lang) {
        List<Weight> weightList = weightDAO.getWeightsByNdbno(ndbno, lang);

        // Para cada medida cacera se calcula la cantidad según el peso dado como parámetro.
        for (Weight weightObj : weightList) {
            float amount = round(weight * weightObj.getAmount() / weightObj.getWeight(), 2);

            weightObj.setAmount(amount);
            weightObj.setWeight(weight);
        }

        return weightList;
    }

    public List<DietType> getAllDietTypes(String lang) {
        return dietTypeDAO.getAllDietTypes(lang);
    }

    private PlanDay getPlanDay(Plan plan, byte day) {
        for (PlanDay planDay : plan.getPlanDayList()) {
            if (planDay.getDay() == day) {
                return planDay;
            }
        }

        return null;
    }
}
