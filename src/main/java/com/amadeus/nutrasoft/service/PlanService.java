package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.calc.Calc;
import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.*;
import com.amadeus.nutrasoft.exception.ApplicationException;
import com.amadeus.nutrasoft.model.*;

import java.util.List;

import static com.amadeus.nutrasoft.calc.Macronutrient.*;
import static com.amadeus.nutrasoft.commons.Utils.*;
import static com.amadeus.nutrasoft.constants.Constants.GENDER_MALE;
import static com.amadeus.nutrasoft.constants.Constants.MACROS_RATIO_TYPE_DEFAULT;
import static com.amadeus.nutrasoft.exception.ApplicationException.PLAN_CAN_NOT_DELETE;

public class PlanService {
    private PlanDAO planDAO = new PlanDAO(MyBatisSqlSession.getSqlSessionFactory());
    private PlanDayDAO planDayDAO = new PlanDayDAO(MyBatisSqlSession.getSqlSessionFactory());
    private PlanDayActivityDAO planDayActivityDAO = new PlanDayActivityDAO(MyBatisSqlSession.getSqlSessionFactory());
    private ExerciseZoneDAO exerciseZoneDAO = new ExerciseZoneDAO(MyBatisSqlSession.getSqlSessionFactory());
    private NutrientRatioDAO nutrientRatioDAO = new NutrientRatioDAO(MyBatisSqlSession.getSqlSessionFactory());
    private ItemDAO itemDAO = new ItemDAO(MyBatisSqlSession.getSqlSessionFactory());
    private ActivityDAO activityDAO = new ActivityDAO(MyBatisSqlSession.getSqlSessionFactory());
    private ComplianceDAO complianceDAO = new ComplianceDAO(MyBatisSqlSession.getSqlSessionFactory());
    private NotificationTempDAO notificationTempDAO = new NotificationTempDAO(MyBatisSqlSession.getSqlSessionFactory());

    private static void avgNutrientRatio(NutrientRatio nutrientRatio) {
        nutrientRatio.setProEnergPct(avg(nutrientRatio.getProEnergPctMin(), nutrientRatio.getProEnergPctMax()));
        nutrientRatio.setChoEnergPct(avg(nutrientRatio.getChoEnergPctMin(), nutrientRatio.getChoEnergPctMax()));
        nutrientRatio.setFatEnergPct((byte) (100 - (nutrientRatio.getProEnergPct() + nutrientRatio.getChoEnergPct())));
    }

    public void calculatePlan(Plan plan) {
        User client = plan.getClient();

        List<ExerciseZone> exerciseZoneList = exerciseZoneDAO.getAllExerciseZones();

        /*
         * Calcula de ratio metabólico basal.
         */

        // Previene cannot unbox null value.
        float hip = plan.getHip() != null ? plan.getHip() : 0;

        final byte bfp = Calc.bfp(client.getGender(), plan.getHeight(), plan.getNeck(), plan.getWaist(), hip);
        final float ffm = Calc.ffm(bfp, plan.getWeight());
        final short bmr = Calc.bmr(ffm);
        plan.setEnergBasal(bmr);

        for (PlanDay planDay : plan.getPlanDayList()) {
            // Nivel de actividad física. Múltiplo del BRM para el total de horas diarias
            // sin incluir el tiempo de entrenamiento.
            float pal = 0;

            // Tiempo de entrenamiento.
            float workoutTime = 0;
            // Tiempo de las actividades sin incluir el entrenamiento.
            float noWorkoutTime = 0;

            // Promedio del ritmo cardiaco en la zona de entrenamiento.
            float hr = 0;

            for (PlanDayActivity planDayActivity : planDay.getPlanDayActivityList()) {
                Activity activity = planDayActivity.getActivity();

                switch (activity.getId()) {
                    case 186:
                    case 187:
                    case 188:
                    case 189:
                    case 190:
                        // Casos de las actividades que corresponden al entrenamiento.
                        // Solo deberá haber una actividad al día de tipo entrenamiento.

                        workoutTime = planDayActivity.getTime();

                        ExerciseZone exerciseZone = getExerciseZone(exerciseZoneList, activity);
                        assert exerciseZone != null;
                        // Obtiene el ritmo cardiaco promedio de la zona de entrenamiento.
                        final float hrMaxPct = avg(exerciseZone.getHrMaxPctMin(), exerciseZone.getHrMaxPctMax());
                        hr = round(valueOfPct(hrMaxPct, plan.getHrMax()));

                        break;
                    default:
                        // Caso de las actividades del resto del día.

                        noWorkoutTime += planDayActivity.getTime();

                        float par = getPar(client.getGender(), activity);
                        pal += planDayActivity.getTime() * par;

                        break;
                }
            }

            /*
             * Calcula el gasto energético total del día y la ingesta energética del día.
             */

            // Gasto energético del las actividades excluyendo el entrenamiento.
            pal /= noWorkoutTime;
            final short noWorkoutEe = Calc.tee(bmr, pal);

            // Gasto energético del entrenamiento.
            final short workoutEe = Calc.ee(client.getGender(), hr, plan.getWeight(), Calc.age(client.getBirthdate()), workoutTime);

            // Gasto energético total del día.
            final short energExpend = (short) (noWorkoutEe + workoutEe);
            planDay.setEnergExpend(energExpend);

            // Ingesta energética del día.
            final short energIntake = Calc.tei(energExpend, plan.getEnergVariationPct());
            planDay.setEnergIntake(energIntake);

            /*
             * Asigna el ratio de macronutrientes por defecto y calcula sus pesos.
             */

            byte proEnergPct;
            byte choEnergPct;
            byte fatEnergPct;

            if (planDay.getMacrosRatioType().equals(MACROS_RATIO_TYPE_DEFAULT)) {
                /*
                 * El ratio de macronutrientes se obiente de la configuración
                 * de la tabla de la base de datos en base al biotipo y a los
                 * objetivos de la dieta
                 */

                // Determina el biotipo.
                final byte ffmi = Calc.ffmi(ffm, plan.getHeight());
                Calc.Biotype biotype = Calc.biotype(client.getGender(), bfp, ffmi);
                plan.setBiotype(biotype.toString());

                NutrientRatio nutrientRatio = new NutrientRatio();
                nutrientRatio.setGoal(plan.getGoal());
                nutrientRatio.setBiotype(plan.getBiotype());

                nutrientRatio = nutrientRatioDAO.getNutrientRatioByParams(nutrientRatio);

                avgNutrientRatio(nutrientRatio);
                proEnergPct = nutrientRatio.getProEnergPct();
                choEnergPct = nutrientRatio.getChoEnergPct();
                fatEnergPct = nutrientRatio.getFatEnergPct();

                planDay.setProEnergPct(proEnergPct);
                planDay.setChoEnergPct(choEnergPct);
                planDay.setFatEnergPct(fatEnergPct);
            } else {
                /*
                 * El ratio de macronutrientes lo establece el usuario y se envía
                 * con la información del plan.
                 */

                proEnergPct = planDay.getProEnergPct();
                choEnergPct = planDay.getChoEnergPct();
            }

            final short proEnerg = (short) valueOfPct(proEnergPct, energIntake);
            final short choEnerg = (short) valueOfPct(choEnergPct, energIntake);
            final short fatEnerg = (short) (energIntake - (proEnerg + choEnerg));

            planDay.setProEnerg(proEnerg);
            planDay.setChoEnerg(choEnerg);
            planDay.setFatEnerg(fatEnerg);

            final float pro = (float) PRO.weight(proEnerg);
            final float cho = (float) CHO.weight(choEnerg);
            final float fat = (float) FAT.weight(fatEnerg);

            planDay.setPro(pro);
            planDay.setCho(cho);
            planDay.setFat(fat);

            float proBodywt = round(pro / plan.getWeight(), 2);
            planDay.setProBodywt(proBodywt);
        }
    }

    public void createPlan(Plan plan) {
        // TODO: Implementar método
        validateCreatePlan(plan);

        planDAO.createPlan(plan);

        for (PlanDay planDay : plan.getPlanDayList()) {
            planDay.setPlan(plan);
            planDayDAO.createPlanDay(planDay);

            for (PlanDayActivity planDayActivity : planDay.getPlanDayActivityList()) {
                planDayActivity.setPlanDay(planDay);
                planDayActivityDAO.createPlanDayActivity(planDayActivity);
            }
        }

        createNotificationToClient(plan.getClient());
    }

    public void updatePlan(Plan plan) {
        // TODO: Implementar método
        validateUpdatePlan(plan);

        // Se elimina y luego se crea porque esta actualización es más compleja.
        planDayActivityDAO.deleteAllPlanDayActivities(plan.getId());

        planDAO.updatePlan(plan);

        for (PlanDay planDay : plan.getPlanDayList()) {
            planDay.setPlan(plan);
            planDayDAO.updatePlanDay(planDay);

            for (PlanDayActivity planDayActivity : planDay.getPlanDayActivityList()) {
                planDayActivity.setPlanDay(planDay);
                planDayActivityDAO.createPlanDayActivity(planDayActivity);
            }
        }
    }

    public void deletePlan(int id) {
        // Valida que el plan no tenga registros de cumplimiento creados.
        int count = complianceDAO.getCountByPlanId(id);
        if (count > 0) {
            throw new ApplicationException(PLAN_CAN_NOT_DELETE);
        }

        planDayActivityDAO.deleteAllPlanDayActivities(id);
        planDayDAO.deleteAllPlanDays(id);
        planDAO.deletePlan(id);
    }

    public Plan getPlanById(int id, String lang) {
        Plan plan = planDAO.getPlanById(id, lang);
        calculatePlan(plan);
        return plan;
    }

    public Plan getPlanByClientId(int clientId, String lang) {
        Plan plan = planDAO.getPlanByClientId(clientId, lang);
        if (plan != null) {
            calculatePlan(plan);
        }

        return plan;
    }

    public List<User> getClientsByCoachId(int coachId, String lang) {
        return planDAO.getClientsByCoachId(coachId, lang);
    }

    public List<Item> getAllGoals(String lang) {
        return itemDAO.getAllGoals(lang);
    }

    public List<Activity> getAllActivities(String lang) {
        return activityDAO.getAllActivities(lang);
    }

    public List<Activity> getDefaultActivities(String lang) {
        return activityDAO.getDefaultActivities(lang);
    }

    private ExerciseZone getExerciseZone(List<ExerciseZone> exerciseZoneList, Activity activity) {
        for (ExerciseZone exerciseZone : exerciseZoneList) {
            if (exerciseZone.getActivity().getId().equals(activity.getId())) {
                return exerciseZone;
            }
        }

        return null;
    }

    private float getPar(String gender, Activity activity) {
        if (gender.equals(GENDER_MALE)) {
            return activity.getMalePar() != null ? activity.getMalePar() : activity.getFemalePar();
        } else {
            return activity.getFemalePar() != null ? activity.getFemalePar() : activity.getMalePar();
        }
    }

    /**
     * Valida que para que se pueda crear un plan el cliente no tenga planes vigentes.
     * La fecha actual debe ser posterior a la fecha fin del último plan que pueda tener
     * el cliente.
     */
    private void validateCreatePlan(Plan plan) {
        // ApplicationException
        // getPlanByClientId
    }

    /**
     * Valida que para que se pueda actualiar un plan este se encuentre vigente.
     * La fecha actual debe ser menor que la fecha fin del plan.
     */
    private void validateUpdatePlan(Plan plan) {
        // ApplicationException
    }

    private void createNotificationToClient(User client) {
        // Obtiene la plantilla de notificación del nuevo plan nutricional.
        Notification notification = notificationTempDAO.getNotificationTempById(2, client.getPreferredLang());
        notification.setNotificationTempId(notification.getId());
        notification.setUser(client);
        // Crea el mensaje personalizado.
        // Hola %s, tienes un nuevo plan nutricional.
        String body = String.format(notification.getBody(), client.getName());
        notification.setBody(body);

        NotificationService notificationService = new NotificationService();
        notificationService.createNotification(notification);
    }
}
