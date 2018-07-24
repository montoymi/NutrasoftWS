package com.amadeus.nutrasoft.constants;

import java.util.Calendar;

public class Constants {
    public static final String GENDER_MALE = "M";
    public static final String GENDER_FEMALE = "F";

    public static final String MACROS_RATIO_TYPE_DEFAULT = "DEF";
    public static final String MACROS_RATIO_TYPE_CUSTOM = "CUS";

    public static final String PART_CODE_PROTEIN = "P";
    public static final String PART_CODE_GARNISH = "G";
    public static final String PART_CODE_SAUCE = "S";

    public static final String FOOD_CLASS_PROTEIN = "PRO";
    public static final String FOOD_CLASS_GRAIN = "GRA";
    public static final String FOOD_CLASS_VEGETABLE = "VEG";

    public static final int DIET_TYPE_DEFAULT = 1;
    public static final byte MEAL_COUNT_DEFAULT = 4;

    // Formatos.
    public static final String SHORT_DATE_FORMAT = "dd/MM/yy";
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DECIMAL_FORMAT_ZERO = "###0.0";
    public static final String DECIMAL_FORMAT = "###0.#";
    public static final String DECIMAL_FORMAT2 = "###0.##";

    public static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    public static String ASSERTION_MESSAGE = "Desconocido %s: %s";
}