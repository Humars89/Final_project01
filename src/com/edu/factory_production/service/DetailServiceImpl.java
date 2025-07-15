package com.edu.factory_production.service;

import com.edu.factory_production.model.*;
import com.edu.factory_production.repository.impl.IDetailsFileRepository;
import com.edu.factory_production.exception.DetailNotFoundException;
import com.edu.factory_production.exception.InvalidWeightException;
import com.edu.factory_production.exception.InvalidDetailStatusException;
import com.edu.factory_production.service.impl.IDetailService;
import com.edu.factory_production.util.IdGenerator;
import java.util.*;
import java.time.LocalDate;
import java.io.IOException;

public class DetailServiceImpl implements IDetailService {
    private final IDetailsFileRepository repository;

    public DetailServiceImpl(IDetailsFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Detail> findBlanks() throws IOException {
        List<Detail> all = repository.findAll();
        all.removeIf(d -> d.getStatus() != Status.BLANK);
        all.sort(Comparator.comparing(Detail::getDateIn));
        return all;
    }

    @Override
    public List<Detail> findReady() throws IOException {
        List<Detail> all = repository.findAll();
        all.removeIf(d -> d.getStatus() != Status.READY);
        all.sort(Comparator.comparing(Detail::getDateOut).reversed());
        return all;
    }

    @Override
    public void addDetail(Detail detail) throws InvalidWeightException, IOException {
        if (detail.getWeight() <= 0 || detail.getWeight() >= 10000) {
            throw new InvalidWeightException("Недопустимый вес детали");
        }
        detail.setId(IdGenerator.generateId()); // Присваиваем сгенерированный ID
        repository.add(detail);
    }

    @Override
    public void editDetail(String id, String name, String material, double weight) throws DetailNotFoundException, InvalidWeightException, IOException {
        Detail detail = repository.findById(id);
        if (detail == null) throw new DetailNotFoundException("Деталь не найдена");
        if (weight <= 0 || weight >= 10000) throw new InvalidWeightException("Недопустимый вес");
        detail.setName(name);
        detail.setMaterial(Material.valueOf(material));
        detail.setWeight(weight);
        repository.update(detail);
    }

    @Override
    public void processBlank(String id) throws DetailNotFoundException, InvalidDetailStatusException, IOException {
        Detail detail = repository.findById(id);
        if (detail == null) throw new DetailNotFoundException("Деталь не найдена");
        if (detail.getStatus() != Status.BLANK) throw new InvalidDetailStatusException("Деталь уже обработана");
        detail.setStatus(Status.READY);
        detail.setDateOut(LocalDate.now());
        repository.update(detail);
    }

    @Override
    public List<Detail> getAll() throws IOException {
        return repository.findAll();
    }

    @Override
    public Detail getById(String id) throws IOException {
        return repository.findById(id);
    }
} 