package com.amadeus.nutrasoft.constants;

import java.util.Calendar;

public class Constants {
    public static final String USER_TYPE_CLIENT = "CLIENT";
    public static final String USER_TYPE_COACH = "COACH";
    public static final byte USER_STATUS_INACTIVE = 0;
    public static final byte USER_STATUS_ACTIVE = 1;
    public static final byte USER_STATUS_APPROVED = 2;

    // Constantes para calcular el plan.
    public static final String GENDER_MALE = "M";
    public static final String MACROS_RATIO_TYPE_DEFAULT = "DEF";

    // Constantes para calcular la dieta.
    public static final String FOOD_TYPE_REGULATOR = "REG";
    public static final String FOOD_CLASS_GRAIN = "GRA";
    public static final String PART_CODE_GARNISH = "G";

    // Preferencias por defecto.
    public static final int DIET_TYPE_DEFAULT = 1;
    public static final byte MEAL_COUNT_DEFAULT = 4;

    // Formatos.
    public static final String SHORT_DATE_FORMAT = "dd/MM/yy";
    public static final String DECIMAL_FORMAT = "###0.#";

    public static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    // Se usa en queries internos donde no es necesario mostrar texto al usuario.
    public static final String LANG_DUMMY = "en";
}