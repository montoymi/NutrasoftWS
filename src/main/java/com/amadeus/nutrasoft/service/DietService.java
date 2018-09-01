package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.calc.DietSolver;
import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.*;
import com.amadeus.nutrasoft.exception.ApplicationException;
import com.amadeus.nutrasoft.model.*;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import static com.amadeus.nutrasoft.commons.Utils.round;
import static com.amadeus.nutrasoft.constants.Constants.FOOD_TYPE_REGULATOR;
import static com.amadeus.nutrasoft.constants.Constants.LANG_DUMMY;
import static com.amadeus.nutrasoft.exception.ApplicationException.MENU_NOT_FOUND;
import static java.util.Calendar.*;

public class DietService {
    private static final Logger LOGGER = Logger.getLogger(DietService.class.getName());

    private PlanDAO planDAO = new PlanDAO(MyBatisSqlSession.getSqlSessionFactory());
    private PreferenceDAO preferenceDAO = new PreferenceDAO(MyBatisSqlSession.getSqlSessionFactory());
    private MenuDAO menuDAO = new MenuDAO(MyBatisSqlSession.getSqlSessionFactory());
    private DietDAO dietDAO = new DietDAO(MyBatisSqlSession.getSqlSessionFactory());
    private WeightDAO weightDAO = new WeightDAO(MyBatisSqlSession.getSqlSessionFactory());
    private DietTypeDAO dietTypeDAO = new DietTypeDAO(MyBatisSqlSession.getSqlSessionFactory());
    private FoodDAO foodDAO = new FoodDAO(MyBatisSqlSession.getSqlSessionFactory());

    /**
     * Genera la dieta en base al plan nutricional del cliente, según el menú del día
     * del plan dado.
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
     * @param day      día ordinal del plan.
     * @return
     */
    public Menu generateDiet(int clientId, byte day) {
        // Obtiene el plan actual del cliente para completar
        // los parámetros de la consulta.
        Plan plan = planDAO.getPlanByClientId(clientId, LANG_DUMMY);
        String lang = plan.getClient().getPreferredLang();

        // Obtiene las preferencias del cliente.
        Preference preference = preferenceDAO.getPreferenceByUserId(clientId, lang);

        // Lista de menus del coach y según el tipo de dieta.
        int coachId = plan.getClient().getCoach().getId();
        int dietTypeId = preference.getDietType().getId();
        List<Menu> menuList = menuDAO.getMenusByCoachIdAndDietTypeId(coachId, dietTypeId);
        if (menuList == null) {
            throw new ApplicationException(MENU_NOT_FOUND);
        }

        Menu menu = getMenu(day, menuList);
        menu.setMealCount(preference.getMealCount());
        menu.setLang(lang);

        // Obtiene la información del menú del día.
        menu = dietDAO.getDietByParams(menu);

        /*
         * Calcula los pesos de los alimentos.
         */

        // Se debe obtener el día de la semana seleccionado. Para esto, se
        // calcula la fecha seleccionada a partir del parámetro day, el cual
        // un número ordinal.
        // 1er día -> amount = 0
        // 2do día -> amount = 1
        int amount = day - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(plan.getBeginDate());
        calendar.add(Calendar.DATE, amount);
        byte dayOfWeek = dayOfWeek(calendar);

        // Obtiene los requerimientos del día de la semana.
        PlanDay planDay = getPlanDay(plan, dayOfWeek);

        for (MenuMeal menuMeal : menu.getMenuMealList()) {
            Dish dish = menuMeal.getDish();

            // Si hay alimentos que están excluidos los cambia por otros alternativos.
            // Solo se cambian lo que no son los reguladores, porque los vegetales y
            // frutas no son los que aportan calorias y se pueden se pueden cambiar
            // según el gusto del cliente.
            for (DishPart dishPart : dish.getDishPartList()) {
                for (DishPartFood dishPartFood : dishPart.getDishPartFoodList()) {
                    Food food = dishPartFood.getFood();
                    if (!food.getFoodGroup().getFoodType().equals(FOOD_TYPE_REGULATOR)) {
                        if (isExcludedFood(food, preference)) {
                            food = foodDAO.getAlternativeFood(food.getFoodGroup().getId(), food.getNdbno(), lang);
                            dishPartFood.setFood(food);
                        }
                    }
                }
            }

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

    /**
     * Obtiene el menu según el día seleccionado por el cliente.
     * Ejemplo: Si se tienen 3 menus registrados.
     * Día  Menu
     * 1    1
     * 2    2
     * 3    3
     * 4    1
     * 5    2
     */
    private Menu getMenu(byte day, List<Menu> menuList) {
        int remainder = day % menuList.size();
        int index;

        if (remainder == 0) {
            // Si el día es múltiplo de la cantidad de menus
            // obtiene el último menu de la lista
            index = menuList.size() - 1;
        } else {
            // Si el día no es múltiplo de la cantidad de menus
            // obtiene el menu que cuya posición en la lista es
            // igual al residuo.
            index = remainder - 1;
        }

        return menuList.get(index);
    }

    private byte dayOfWeek(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            case SATURDAY:
                return 6;
            default:
                return 7;
        }
    }

    private PlanDay getPlanDay(Plan plan, byte day) {
        for (PlanDay planDay : plan.getPlanDayList()) {
            if (planDay.getDay() == day) {
                return planDay;
            }
        }

        return null;
    }

    private boolean isExcludedFood(Food food, Preference preference) {
        for (ExcludedFood excludedFood : preference.getExcludedFoodList()) {
            if (excludedFood.getFood().getNdbno().equals(food.getNdbno())) {
                return true;
            }
        }

        return false;
    }
}
