package com.edu.factory_production.ui;

import com.edu.factory_production.controller.impl.IDetailController;
import com.edu.factory_production.model.DetailFactory;
import com.edu.factory_production.model.*;
import com.edu.factory_production.exception.DetailNotFoundException;
import com.edu.factory_production.exception.InvalidWeightException;
import com.edu.factory_production.exception.InvalidDetailStatusException;
import com.edu.factory_production.repository.impl.IDetailsFileRepository;
import com.edu.factory_production.util.IdGenerator;
import java.io.IOException;
import java.util.*;

public class ConsoleUI {
    private final IDetailController controller;
    private final DetailFactory detailFactory;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(IDetailController controller, DetailFactory detailFactory, IDetailsFileRepository repository) {
        this.controller = controller;
        this.detailFactory = detailFactory;
        try {
            List<Detail> existingDetails = repository.findAll();
            IdGenerator.initializeCounter(existingDetails);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении существующих деталей для инициализации генератора ID: " + e.getMessage());
            System.err.println("Генератор ID будет инициализирован с начальным значением, что может привести к дубликатам.");
        }
    }

    public void start() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        showBlanks();
                        break;
                    case "2":
                        showReady();
                        break;
                    case "3":
                        addDetail();
                        break;
                    case "4":
                        editDetail();
                        break;
                    case "5":
                        processBlank();
                        break;
                    case "0":
                        System.out.println("Выход...");
                        return;
                    default:
                        System.out.println("Неверный выбор");
                }
            } catch (DetailNotFoundException | InvalidWeightException | InvalidDetailStatusException | IOException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- МЕНЮ ---");
        System.out.println("1. Найти все заготовки для обработки");
        System.out.println("2. Найти все готовые детали");
        System.out.println("3. Добавить новую деталь");
        System.out.println("4. Редактировать информацию о детали");
        System.out.println("5. Отправить заготовку на обработку");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void showBlanks() throws IOException {
        List<Detail> blanks = controller.getBlanks();
        System.out.println("\nЗаготовки:");
        System.out.println("ID | Название | Материал | Вес | Дата поступления");
        for (Detail d : blanks) {
            System.out.printf("%s | %s | %s | %.2f | %s\n", d.getId(), d.getName(), d.getMaterial(), d.getWeight(), d.getDateIn());
        }
    }

    private void showReady() throws IOException {
        List<Detail> ready = controller.getReady();
        System.out.println("\nГотовые детали:");
        System.out.println("ID | Название | Материал | Вес | Статус | Дата поступления | Дата завершения");
        for (Detail d : ready) {
            System.out.printf("%s | %s | %s | %.2f | %s | %s | %s\n", d.getId(), d.getName(), d.getMaterial(), d.getWeight(), d.getStatus(), d.getDateIn(), d.getDateOut());
        }
    }

    private void addDetail() throws InvalidWeightException, IOException {
        System.out.print("Название: ");
        String name = scanner.nextLine();
        Material material = chooseMaterial();
        double weight = readWeight();
        Detail detail = detailFactory.createDetail(name, material, weight);
        controller.addDetail(detail);
        System.out.println("Деталь добавлена!");
        showBlanks();
    }

    private double readWeight() {
        while (true) {
            System.out.print("Вес: ");
            String input = scanner.nextLine().replace(',', '.');
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите число (например, 10.5 или 10,5)");
            }
        }
    }

    private Material chooseMaterial() {
        Material[] materials = Material.values();
        System.out.println("Выберите материал:");
        for (int i = 0; i < materials.length; i++) {
            System.out.printf("%d) %s\n", i + 1, materials[i]);
        }
        int choice = -1;
        while (choice < 1 || choice > materials.length) {
            System.out.print("Введите номер материала: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите число.");
            }
        }
        return materials[choice - 1];
    }

    private void editDetail() throws DetailNotFoundException, InvalidWeightException, IOException {
        System.out.print("ID детали: ");
        String id = scanner.nextLine();
        System.out.print("Новое название: ");
        String name = scanner.nextLine();
        Material material = chooseMaterial();
        double weight = readWeight();
        controller.editDetail(id, name, material.name(), weight);
        System.out.println("Деталь обновлена!");
    }

    private void processBlank() throws DetailNotFoundException, InvalidDetailStatusException, IOException {
        System.out.print("ID заготовки: ");
        String id = scanner.nextLine();
        controller.processBlank(id);
        System.out.println("Деталь обработана!");
    }
} 