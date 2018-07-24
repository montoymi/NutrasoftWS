package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.calc.Calc;
import com.amadeus.nutrasoft.dao.PlanDAO;
import com.amadeus.nutrasoft.exception.ApplicationException;
import com.amadeus.nutrasoft.model.Measurement;
import com.amadeus.nutrasoft.model.Plan;
import com.amadeus.nutrasoft.model.User;
import com.amadeus.nutrasoft.mybatis.MyBatisSqlSession;

import java.util.Calendar;

import static com.amadeus.nutrasoft.constants.Constants.CURRENT_YEAR;
import static com.amadeus.nutrasoft.exception.ApplicationException.MEAS_PLAN_NOT_FOUND;

public class MeasurementService {
    private PlanDAO planDAO = new PlanDAO(MyBatisSqlSession.getSqlSessionFactory());

    public Measurement calculateMeasurement(int clientId) {
        // Obtiene el plan actual del cliente.
        // El idioma no es importante porque lo que se necesita son solo las medidas.
        Plan plan = planDAO.getPlanByClientId(clientId, "es");
        if (plan == null) {
            throw new ApplicationException(MEAS_PLAN_NOT_FOUND);
        }

        User client = plan.getClient();

        /*
         * Calcula de porcentaje de grasa corporal.
         */

        final byte fatPct = Calc.bfp(client.getGender(), plan.getHeight(), plan.getNeck(), plan.getWaist(), plan.getHip());

        /*
         * Calcula de porcentaje de agua corporal.
         */

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(client.getBirthdate());
        float age = CURRENT_YEAR - calendar.get(Calendar.YEAR);

        final byte waterPct = Calc.bwp(client.getGender(), age, plan.getHeight(), plan.getWeight());

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
