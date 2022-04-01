package com.humga.moneytransferservice.utils;

import java.util.Arrays;

public class Utils {
    /**
     * Форматирует целочисленное значение номера карты в строку формата 0000-0000-0000-0000
     *
     * @param number - номер карты
     * @return - строка в указанном формате
     */
    public static String formatCardNumber (long number) {
         return Arrays.stream(Long.toString(number).split("(?<=\\G\\d{4})"))
                 .reduce("",(s1, s2) -> s1.isEmpty()? s2:s1 + "-"+ s2);
    }
}
