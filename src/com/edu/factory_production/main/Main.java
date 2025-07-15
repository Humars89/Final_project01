package com.edu.factory_production.main;

import com.edu.factory_production.repository.impl.IDetailsFileRepository;
import com.edu.factory_production.repository.DetailsFileRepository;
import com.edu.factory_production.service.impl.IDetailService;
import com.edu.factory_production.service.DetailServiceImpl;
import com.edu.factory_production.controller.impl.IDetailController;
import com.edu.factory_production.controller.DetailController;
import com.edu.factory_production.ui.ConsoleUI;
import com.edu.factory_production.model.DetailFactory;

public class Main {
    public static void main(String[] args) {
        IDetailsFileRepository repo = new DetailsFileRepository();
        IDetailService service = new DetailServiceImpl(repo);
        IDetailController controller = new DetailController(service);
        DetailFactory detailFactory = new DetailFactory();
        ConsoleUI ui = new ConsoleUI(controller, detailFactory, repo);
        ui.start();
    }
} 