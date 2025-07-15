package com.edu.factory_production.util;

public class Validator {
    public static boolean isValidWeight(double weight) {
        return weight > 0 && weight < 10000;
    }
} 