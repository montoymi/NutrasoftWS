package com.amadeus.nutrasoft.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.amadeus.nutrasoft.constants.Constants.DECIMAL_FORMAT;
import static com.amadeus.nutrasoft.constants.Constants.SHORT_DATE_FORMAT;

public class Utils {
    /*
     * Funciones aritméticas.
     */

    /**
     * Retorna el valor de un porcentaje de un total.
     * <pre>
     * value = pct * total / 100
     * </pre>
     */
    public static float valueOfPct(float pct, float total) {
        return pct * total / 100;
    }

    /**
     * Retorna el valor de un porcentaje de un total.
     * <pre>
     * value = pct * total / 100
     * </pre>
     */
    public static double valueOfPct(double pct, double total) {
        return pct * total / 100;
    }

    /**
     * Retorna el porcentaje de un número respecto a un total.
     * <pre>
     * pct = value * 100 / total
     * </pre>
     */
    public static float pct(float value, float total) {
        return value * 100 / total;
    }

    /**
     * Retorna el porcentaje de un número respecto a un total.
     * <pre>
     * pct = value * 100 / total
     * </pre>
     */
    public static double pct(double value, double total) {
        return value * 100 / total;
    }

    /**
     * Retorna un double con redondeo HALF_UP.
     * Precision is the number of digits in a number. Scale is the number of digits to the right of the decimal point in a number. For example, the
     * number 123.45 has a precision of 5 and a scale of 2.
     */
    public static float round(float value) {
        return round(value, 0);
    }

    public static float round(float value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).floatValue();
    }

    /**
     * Retorna un double con redondeo HALF_UP.
     * Precision is the number of digits in a number. Scale is the number of digits to the right of the decimal point in a number. For example, the
     * number 123.45 has a precision of 5 and a scale of 2.
     */
    public static double round(double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static double avg(double value1, double value2) {
        return (value1 + value2) / 2;
    }

    public static byte avg(byte value1, byte value2) {
        return (byte) round((value1 + value2) / 2);
    }

    /*
     * Funciones de formato.
     */

    public static String format(Object value) {
        String pattern = value instanceof Number ? DECIMAL_FORMAT : null;
        return format(value, pattern);
    }

    public static String format(Object value, String pattern) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return createDecimalFormat(pattern).format(value);
        } else if (value instanceof Date) {
            return createDateFormat().format(value);
        } else {
            return null;
        }
    }

    /*
     * Funciones privadas para formato y conversión.
     */

    /**
     * Retorna un SimpleDateFormat con formato día/mes/año.
     */
    private static SimpleDateFormat createDateFormat() {
        return createDateFormat(SHORT_DATE_FORMAT);
    }

    /**
     * Retorna un SimpleDateFormat.
     */
    public static SimpleDateFormat createDateFormat(String pattern) {
        return new SimpleDateFormat(pattern, new Locale("es_PE"));
    }

    /**
     * Retorna un DecimalFormat. El separador de grupo es la coma y el separador decimal
     * es el punto.
     */
    private static DecimalFormat createDecimalFormat(String pattern) {
        return createDecimalFormat(pattern, ',', '.');
    }

    /**
     * Retorna un DecimalFormat.
     */
    private static DecimalFormat createDecimalFormat(String pattern, char groupingSeparator, char decimalSeparator) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern(pattern);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(groupingSeparator);
        symbols.setDecimalSeparator(decimalSeparator);
        format.setDecimalFormatSymbols(symbols);
        return format;
    }
}

