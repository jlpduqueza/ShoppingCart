package com.example.shoppingapp.controller;

public class ValidationHelper {
    public Boolean isNumeric(String stringToInt) {
        if (!stringToInt.matches("^[0-9]+$")) {
            return false;
        }

        try {
            Integer.parseInt(stringToInt);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
