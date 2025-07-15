package com.edu.factory_production.controller;

import com.edu.factory_production.model.*;
import com.edu.factory_production.service.impl.IDetailService;
import com.edu.factory_production.exception.DetailNotFoundException;
import com.edu.factory_production.exception.InvalidWeightException;
import com.edu.factory_production.exception.InvalidDetailStatusException;
import com.edu.factory_production.controller.impl.IDetailController;
import java.io.IOException;
import java.util.*;

public class DetailController implements IDetailController {
    private final IDetailService service;

    public DetailController(IDetailService service) {
        this.service = service;
    }

    public List<Detail> getBlanks() throws IOException {
        return service.findBlanks();
    }

    public List<Detail> getReady() throws IOException {
        return service.findReady();
    }

    public void addDetail(Detail detail) throws InvalidWeightException, IOException {
        service.addDetail(detail);
    }

    public void editDetail(String id, String name, String material, double weight) throws DetailNotFoundException, InvalidWeightException, IOException {
        service.editDetail(id, name, material, weight);
    }

    public void processBlank(String id) throws DetailNotFoundException, InvalidDetailStatusException, IOException {
        service.processBlank(id);
    }

    public List<Detail> getAll() throws IOException {
        return service.getAll();
    }

    public Detail getById(String id) throws IOException {
        return service.getById(id);
    }
} 