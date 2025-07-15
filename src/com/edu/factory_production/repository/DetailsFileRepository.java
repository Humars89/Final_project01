package com.edu.factory_production.repository;

import com.edu.factory_production.model.*;
import com.edu.factory_production.repository.impl.IDetailsFileRepository;
import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class DetailsFileRepository implements IDetailsFileRepository {
    private static final String FILE_NAME = "details.txt";

    public List<Detail> findAll() throws IOException {
        List<Detail> details = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    Detail detail = new Detail(
                        parts[0].trim(),
                        parts[1].trim(),
                        Material.valueOf(parts[2].trim()),
                        Double.parseDouble(parts[3].trim().replace(',', '.')),
                        Status.valueOf(parts[4].trim()),
                        LocalDate.parse(parts[5].trim()),
                        parts[6].trim().equals("null") ? null : LocalDate.parse(parts[6].trim())
                    );
                    details.add(detail);
                }
            }
        } catch (FileNotFoundException e) {
            // файл может отсутствовать, это не ошибка
        }
        return details;
    }

    public void saveAll(List<Detail> details) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Detail d : details) {
                bw.write(String.format("%s | %s | %s | %.2f | %s | %s | %s\n",
                    d.getId(), d.getName(), d.getMaterial(), d.getWeight(), d.getStatus(), d.getDateIn(), d.getDateOut()));
            }
        }
    }

    public Detail findById(String id) throws IOException {
        return findAll().stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null);
    }

    public void add(Detail detail) throws IOException {
        List<Detail> details = findAll();
        details.add(detail);
        saveAll(details);
    }

    public void update(Detail detail) throws IOException {
        List<Detail> details = findAll();
        for (int i = 0; i < details.size(); i++) {
            if (details.get(i).getId().equals(detail.getId())) {
                details.set(i, detail);
                break;
            }
        }
        saveAll(details);
    }
} 