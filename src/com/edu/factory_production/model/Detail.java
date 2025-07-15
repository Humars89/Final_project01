package com.edu.factory_production.model;

import java.time.LocalDate;
import java.util.Objects;

public class Detail {
    private String id;
    private String name;
    private Material material;
    private double weight;
    private Status status;
    private LocalDate dateIn;
    private LocalDate dateOut;

    public Detail() {}

    public Detail(String id, String name, Material material, double weight, Status status, LocalDate dateIn, LocalDate dateOut) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.weight = weight;
        this.status = status;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDate getDateIn() { return dateIn; }
    public void setDateIn(LocalDate dateIn) { this.dateIn = dateIn; }
    public LocalDate getDateOut() { return dateOut; }
    public void setDateOut(LocalDate dateOut) { this.dateOut = dateOut; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detail detail = (Detail) o;
        return Double.compare(detail.weight, weight) == 0 &&
                Objects.equals(id, detail.id) &&
                Objects.equals(name, detail.name) &&
                material == detail.material &&
                status == detail.status &&
                Objects.equals(dateIn, detail.dateIn) &&
                Objects.equals(dateOut, detail.dateOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, material, weight, status, dateIn, dateOut);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %.2f | %s | %s | %s", id, name, material, weight, status, dateIn, dateOut);
    }
} 