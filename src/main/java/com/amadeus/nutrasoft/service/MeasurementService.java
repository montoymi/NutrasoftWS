package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.calc.Calc;
import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.PlanDAO;
import com.amadeus.nutrasoft.exception.ApplicationException;
import com.amadeus.nutrasoft.model.Measurement;
import com.amadeus.nutrasoft.model.Plan;
import com.amadeus.nutrasoft.model.User;

import static com.amadeus.nutrasoft.constants.Constants.LANG_DUMMY;
import static com.amadeus.nutrasoft.exception.ApplicationException.PLAN_NOT_FOUND;

public class MeasurementService {
    private PlanDAO planDAO = new PlanDAO(MyBatisSqlSession.getSqlSessionFactory());

    public Measurement getMeasurementByClientId(int clientId) {
        // Obtiene el plan actual del cliente.
        Plan plan = planDAO.getPlanByClientId(clientId, LANG_DUMMY);
        if (plan == null) {
            throw new ApplicationException(PLAN_NOT_FOUND);
        }

        User client = plan.getClient();

        /*
         * Calcula de porcentaje de grasa corporal.
         */

        // Previene cannot unbox null value.
        float hip = plan.getHip() != null ? plan.getHip() : 0;

        final byte fatPct = Calc.bfp(client.getGender(), plan.getHeight(), plan.getNeck(), plan.getWaist(), hip);

        /*
         * Calcula de porcentaje de agua corporal.
         */

        final byte waterPct = Calc.bwp(client.getGender(), Calc.age(client.getBirthdate()), plan.getHeight(), plan.getWeight());

        /*
         * Calcula de porcentaje de hueso corporal.
         */

        final byte bonePct = Calc.bbp();

        /*
         * Calcula de porcentaje de masa muscular (incluye organos y tejidos).
         */

        final byte musclePct = (byte) (100 - (fatPct + waterPct + bonePct));

        /*
         * Crea el measurement.
         */

        Measurement measurement = new Measurement();
        measurement.setHeight(plan.getHeight());
        measurement.setNeck(plan.getNeck());
        measurement.setWaist(plan.getWaist());
        measurement.setHip(plan.getHip());
        measurement.setWeight(plan.getWeight());
        measurement.setFatPct(fatPct);
        measurement.setWaterPct(waterPct);
        measurement.setBonePct(bonePct);
        measurement.setMusclePct(musclePct);

        return measurement;
    }
}
