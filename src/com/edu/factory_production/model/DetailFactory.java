package com.edu.factory_production.model;

import java.time.LocalDate;

public class DetailFactory {
    public Detail createDetail(String name, Material material, double weight) {
        return new Detail(null, name, material, weight, Status.BLANK, LocalDate.now(), null);
    }
} 