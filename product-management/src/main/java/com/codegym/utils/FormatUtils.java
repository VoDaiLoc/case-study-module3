package com.codegym.utils;

import java.text.DecimalFormat;

public class FormatUtils {
    public static String doubleToVND(double value) {
        String patternVND = ",###₫";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(value);
    }

    public static String doubleToKg(double value) {
        String patternVND = ",###Kg";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(value);
    }
}
