package com.edu.factory_production.repository.impl;

import com.edu.factory_production.model.Detail;
import java.io.IOException;
import java.util.List;

public interface IDetailsFileRepository {
    List<Detail> findAll() throws IOException;
    void saveAll(List<Detail> details) throws IOException;
    Detail findById(String id) throws IOException;
    void add(Detail detail) throws IOException;
    void update(Detail detail) throws IOException;
} 