package com.epam.esm.utils;

public class ServiceUtils {

    public static boolean checkIsNumeric(String id) {
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
