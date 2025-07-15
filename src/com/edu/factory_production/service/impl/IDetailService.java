package com.edu.factory_production.service.impl;

import com.edu.factory_production.model.Detail;
import com.edu.factory_production.exception.DetailNotFoundException;
import com.edu.factory_production.exception.InvalidWeightException;
import com.edu.factory_production.exception.InvalidDetailStatusException;
import java.io.IOException;
import java.util.List;

public interface IDetailService {
    List<Detail> findBlanks() throws IOException;
    List<Detail> findReady() throws IOException;
    void addDetail(Detail detail) throws InvalidWeightException, IOException;
    void editDetail(String id, String name, String material, double weight) throws DetailNotFoundException, InvalidWeightException, IOException;
    void processBlank(String id) throws DetailNotFoundException, InvalidDetailStatusException, IOException;
    List<Detail> getAll() throws IOException;
    Detail getById(String id) throws IOException;
} 