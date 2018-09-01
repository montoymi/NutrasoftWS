package com.amadeus.nutrasoft.calc;

import java.util.Calendar;
import java.util.Date;

import static com.amadeus.nutrasoft.calc.Calc.Biotype.*;
import static com.amadeus.nutrasoft.commons.Utils.*;
import static com.amadeus.nutrasoft.constants.Constants.CURRENT_YEAR;
import static com.amadeus.nutrasoft.constants.Constants.GENDER_MALE;
import static java.lang.Math.log10;
import static java.lang.Math.pow;

/**
 * <b>Basal metabolic rate (BMR):</b> The minimal rate of energy expenditure compatible with life. It is measured in the supine position
 * under standard conditions of rest, fasting, immobility, thermoneutrality and mental relaxation. Depending on its use, the rate is
 * usually expressed per minute, per hour or per 24 hours.
 * <p>
 * <b>Energy requirement (ER):</b> The amount of food energy needed to balance energy expenditure in order to maintain body size, body
 * composition and a level of necessary and desirable physical activity, and to allow optimal growth and development of children,
 * deposition of tissues during pregnancy, and secretion of milk during lactation, consistent with long-term good health. For healthy,
 * well-nourished adults, it is equivalent to total energy expenditure. There are additional energy needs to support growth in children
 * and in women during pregnancy, and for milk production during lactation.
 * <p>
 * <b>Total energy expenditure (TEE):</b> The energy spent, on average, in a 24-hour period by an individual or a group of individuals.
 * By definition, it reflects the average amount of energy spent in a typical day, but it is not the exact amount of energy spent each
 * and every day.
 * <p>
 * <b>Physical activity level (PAL):</b> TEE for 24 hours expressed as a multiple of BMR, and calculated as TEE/BMR for 24 hours. In
 * adult men and non-pregnant, non-lactating women, BMR times PAL is equal to TEE or the daily energy requirement.
 * <p>
 * <b>Physical activity ratio (PAR):</b> The energy cost of an activity per unit of time (usually a minute or an hour) expressed as a
 * multiple of BMR. It is calculated as energy spent in an activity/BMR, for the selected time unit.
 */

/*
 * Sobre los tipos de datos:
 *
 * Las siguientes variables y métodos deberán ser byte:
 *      Porcentajes.
 *      Índices.
 * Las siguientes variables y métodos deberán ser short:
 *      Energía: tasa metabolica basal, gasto energético, etc.
 * Las siguientes variables y métodos deberán ser float:
 *      Medidas corporales.
 *      Peso corporal: weight, ffm, etc.
 * Las siguientes variables deberán ser double:
 *      Todos a las que le asigne el resultado de un cálculo, ya que este es el tipo de dato en el que java ejecuta en las operaciones
 *      con decimales. La conversion se hará antes de retornar el valor.
 */
public class Calc {
    // Se utiliza el BFP para calcular el nivel de grasa corporal y así estimar el biotipo endomorfo.
    private static final byte MALE_OBESITY_BFP = 25;
    private static final byte FEMALE_OBESITY_BFP = 32;

    // Se utiliza el FFMI para calcular el nivel de desarrollo muscular y así estimar el biotipo mesomorfo.
    private static final byte MALE_NOTABLE_MUSCULATURE_FFMI = 22;
    private static final byte FEMALE_NOTABLE_MUSCULATURE_FFMI = 17;

    public static float age(Date birthdate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthdate);
        return CURRENT_YEAR - calendar.get(Calendar.YEAR);
    }

    /*
     * Métodos para calcular la composición corporal.
     */

    /**
     * Percent Body Fat (%BF): The percent body fat is calculated using the formulas developed by Hodgdon and Beckett at the Naval Health
     * Research Center in 1984. The formulas require the measurements to be in centimeters with an accuracy of 0.5 cm:
     * <pre>
     * Males:
     * %BF = 495 / (1.0324 - 0.19077(log(w - n)) + 0.15456(log(H))) - 450
     * Females:
     * %BF = 495 / (1.29579 - 0.35004(log(w + h - n)) + 0.22100(log(H))) - 450
     * </pre>
     * Where: %BF = percent of body fat; w = waist, cm; n = neck, cm; h = hip, cm; H = height, cm.
     * <p>
     * Valores del %BF para hombres:
     * <ul>
     * <li>Grasa esencial: 2-5%
     * <li>Atletas: 6-13%
     * <li>Fitness: 14-17%
     * <li>Promedio: 18-24%
     * <li>Obesidad: 25%+
     * </ul>
     * <p>
     * Valores del %BF para mujeres:
     * <ul>
     * <li>Grasa esencial: 10-13%
     * <li>Atletas: 14-20%
     * <li>Fitness: 21-24%
     * <li>Promedio: 25-31%
     * <li>Obesidad: 32%+
     * </ul>
     * <p>
     * Source: J. Hodgdon, and M. Beckett, Prediction of percent body fat for U.S. Navy men and women from body circumferences and height.
     * Reports No. 84-29 and 84-11. Naval Health Research Center, San Diego, Cal. 1984.
     *
     * @return The percent body fat
     */
    public static byte bfp(String gender, float height, float neck, float waist, float hip) {
        double bfp;

        height *= 100;

        if (gender.equals(GENDER_MALE)) {
            bfp = 495 / (1.0324 - 0.19077 * log10(waist - neck) + 0.15456 * log10(height)) - 450;
        } else {
            bfp = 495 / (1.29579 - 0.35004 * log10(waist + hip - neck) + 0.22100 * log10(height)) - 450;
        }

        return (byte) round(bfp, 0);
    }

    /**
     * Fat Free Mass (FFM): That part of the body comprised of muscle, bone, tissue, water, and all other fat free weight in the body:
     * <pre>
     * FFM = W x (100 - %BF) / 100
     * </pre>
     * Where FFM = Fat Free Mass, kg; W = weight, kg; BF = percent of body fat.
     *
     * @return The fat free weight
     */
    public static float ffm(byte bfp, float weight) {
        return valueOfPct(100 - bfp, weight);
    }

    /**
     * Fat Free Mass Index (FFMI): The FFMI provides a more direct estimate of muscularity because it eliminates body fat by calculating
     * the lean BMI and then adding a small correction for height:
     * <pre>
     * FFMI = FFM/H<sup>2</sup> + 6.1 x (1.8 - H)
     * </pre>
     * Where FFMI = fat free weight index, kg/m<sup>2</sup>; FFM = fat free weight, kg; H = height, m.
     * <p>
     * Valores del FFMI para hombres:
     * <ul>
     * <li>Baja musculatura: 18
     * <li>Musculatura normal: 20
     * <li>Musculatura destacable: 22
     * <li>Desarrollo muscular con entrenamiento: 23+
     * <li>Límite de desarrollo muscular natural: 25
     * </ul>
     * <p>
     * Valores del FFMI para mujeres:
     * <ul>
     * <li>Baja musculatura: 13
     * <li>Musculatura normal: 15
     * <li>Musculatura destacable: 17
     * <li>Límite de desarrollo muscular natural: 21
     * </ul>
     * <p>
     * Source: Kouri, E.M., Pope, H.G., Katz, D.L., & Oliva, P. (1995). Fat-free weight index in users and nonusers of anabolicandrogenic
     * steroids. Clinical Journal of Sports Medicine, 5, 223–228.
     *
     * @return The fat free weight index
     */
    public static byte ffmi(float ffm, float height) {
        double ffmi = ffm / pow(height, 2) + 6.1 * (1.8 - height);
        return (byte) round(ffmi, 0);
    }

    /**
     * Total Body Water (TBW): Total body water prediction equations for adults of any age.
     * <pre>
     * Males:
     * TBW = 2.447 - 0.09516 x A + 0.1074 x H + 0.3362 x W
     * Females:
     * TBW = -2.097 + 0.1069 x H + 0.2466 x W
     * </pre>
     * Where: A = Age, years; H = height, cm; W = weight, kg;
     * <p>
     * Source: Watson PE, Watson ID, Batt RD. Total body water volumes for adult males and females estimated from simple anthropometric
     * measurements. Am J Clin Nutr 33:27-39, 1980.
     *
     * @return The total body water
     */
    public static float tbw(String gender, float age, float height, float weight) {
        double tbw;

        height *= 100;

        if (gender.equals(GENDER_MALE)) {
            tbw = 2.447 - 0.09516 * age + 0.1074 * height + 0.3362 * weight;
        } else {
            tbw = -2.097 + 0.1069 * height + 0.2466 * weight;
        }

        return (float) tbw;
    }

    /**
     * Percent Body Water (%BW): The percent body water.
     *
     * @return The percent body bone
     */
    public static byte bwp(String gender, float age, float height, float weight) {
        float tbw = tbw(gender, age, height, weight);
        return (byte) round(pct(tbw, weight));
    }

    /**
     * Percent Body Fat (%BB): The percent body bone.
     * As a percentage of body weight, the dry, fat-free skeleton comprises about 3% of the body weight in the fetus and newborn and about
     * 6% to 7% of body weight in the adult.
     * Human body composition, Volume 918, by Steven Heymsfield, Timothy Lohman, ZiMian Wang, and Scott Going p. 291
     *
     * @return The percent body bone
     */
    public static byte bbp() {
        return 7;
    }

    /*
     * Métodos para calcular el requerimiento energético.
     */

    /**
     * This formula from Katch & McArdle takes into account lean weight and therefore is more accurate than a formula based on total body
     * weight. The Harris Benedict equation has separate formulas for men and women because men generally have a higher FFM and this is
     * factored into the men's formula. Since the Katch-McArdle formula accounts for FFM, this single formula applies equally to both men
     * and women:
     * <pre>
     * BMR = 370 + 21.6 x FFM
     * </pre>
     * Where BMR = basal metabolic rate, kcal/day; FFM = fat free weight, kg.
     * <p>
     * Source: Katch, Frank, Katch, Victor, McArdle, William. Exercise Physiology: Energy, Nutrition, and Human Performance, 4th edition.
     * Williams & Wilkins, 1996.
     *
     * @return The basal metabolic rate
     */
    public static short bmr(float ffm) {
        double bmr = 370 + 21.6 * ffm;
        return (short) round(bmr, 0);
    }

    /**
     * Total energy expenditure (TEE): The energy spent, on average, in a 24-hour period by an individual or a group of individuals. By
     * definition, it reflects the average amount of energy spent in a typical day, but it is not the exact amount of energy spent each
     * and every day.
     *
     * @return The total energy expenditure
     */
    public static short tee(short bmr, float pal) {
        // Siempre será entero dado que bmr es un entero de 4 cifras, y pal un decimal de precisión 3 y escala 2.
        double tee = bmr * pal;
        return (short) round(tee, 0);
    }

    /**
     * Total energy intake: The energy intake, in a 24-hour period.
     *
     * @param tee          The total energy expenditure
     * @param variationPct Tiene un valor por defecto que depende del objetivo de la dieta y puede ser modificado por el coach.
     * @return the total energy intake
     */
    public static short tei(short tee, byte variationPct) {
        double tei = tee * (1 + variationPct / 100.00);
        return (short) round(tei, 0);
    }

    public static float hrMax(float age) {
        return 220 - age;
    }

    /**
     * Formulas for determination of calorie burn if VO2max is unknown
     * <pre>
     * Males:
     * EE = ((-55.0969 + (0.6309 x HR) + (0.1988 x W) + (0.2017 x A))/4.184) x 60 x T
     * Females:
     * EE =  ((-20.4022 + (0.4472 x HR) - (0.1263 x W) + (0.074 x A))/4.184) x 60 x T
     * </pre>
     * Where EE = energy intake, kcal; HR = heart rate, beats/minute, W = weight, kg; A = age, years; T = time, hours
     *
     * @return the energy expenditure from heart rate
     */
    public static short ee(String gender, float hr, float weight, float age, float time) {
        double ee;

        if (gender.equals(GENDER_MALE)) {
            ee = ((-55.0969 + 0.6309 * hr + 0.1988 * weight + 0.2017 * age) / 4.184) * 60 * time;
        } else {
            ee = ((-20.4022 + 0.4472 * hr - 0.1263 * weight + 0.074 * age) / 4.184) * 60 * time;
        }

        return (short) round(ee, 0);
    }

    /*
     * El biotipo se calcula en base a los tres componentes: ectomorfo, mesomorfo y endomorfo. Para ello se toman diferentes medidas y
     * se aplican fórmulas. De forma práctica se estima en base a los indicadores desarrollados en esta clase.
     */
    public static Biotype biotype(String gender, byte bfp, byte ffmi) {
        byte OBESITY_BFP = gender.equals(GENDER_MALE) ? MALE_OBESITY_BFP : FEMALE_OBESITY_BFP;
        byte NOTABLE_MUSCULATURE_FFMI = gender.equals(GENDER_MALE) ? MALE_NOTABLE_MUSCULATURE_FFMI : FEMALE_NOTABLE_MUSCULATURE_FFMI;

        if (bfp >= OBESITY_BFP) {
            return ENDOMORPH;
        } else if (ffmi >= NOTABLE_MUSCULATURE_FFMI) {
            return MESOMORPH;
        } else {
            return ECTOMORPH;
        }
    }

    public enum Biotype {
        ECTOMORPH("ECT"),
        MESOMORPH("MES"),
        ENDOMORPH("END");

        private final String code;

        Biotype(final String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}
