package com.example.motocatalog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.motocatalog.beans.Brand;
import com.example.motocatalog.beans.Motorcycle;
import com.example.motocatalog.beans.SearchCondition;
import com.example.motocatalog.services.MotosService;

@Controller
public class MotosController {

    private static final Logger log = LoggerFactory.getLogger(MotosController.class);

    @Autowired
    MotosService service;

    @RequestMapping("/")
    public String root() {
        return "redirect:/motos";
    }

    @GetMapping("/motos")
    public String motos(Model model) {

        // ブランド
        List<Brand> brands = new ArrayList<>();
        brands = service.getBrands();

        // バイク
        List<Motorcycle> motos = new ArrayList<>();
        SearchCondition condition = new SearchCondition();
        motos = service.getMotos(condition);

        model.addAttribute("brands", brands);
        model.addAttribute("motos", motos);
        log.info("motos: {}", motos);

        return "moto_list";
    }
}
