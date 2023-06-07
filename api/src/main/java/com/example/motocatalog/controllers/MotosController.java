package com.example.motocatalog.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.motocatalog.bean.Brand;
import com.example.motocatalog.bean.Motorcycle;

@Controller
public class MotosController {
    
    @RequestMapping("/hello")
    public String hello(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/motos")
    public String motos(Model model) {
        // ブランド
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand("01", "HONDA"));
        brands.add(new Brand("02", "KAWASAKI"));
        brands.add(new Brand("03", "YAMAHA"));
        brands.add(new Brand("04", "SUZUKI"));

        // バイク
        List<Motorcycle> motos = new ArrayList<>();
        LocalDateTime curDateTime = LocalDateTime.now();
        motos.add(
            new Motorcycle(1, "GB350", 800, 1, "空冷", 500000, "いい音",
            new Brand("01", "HONDA"),
            1, curDateTime, curDateTime)
            );
        motos.add(
            new Motorcycle(2, "Ninja", 800, 2, "水冷", 1000000, "はやい",
            new Brand("02", "KAWASAKI"),
            1, curDateTime, curDateTime)
            );
        motos.add(
            new Motorcycle(3, "Z900RS CAFE", 820, 4, "水冷", 1380000, "はやい",
            new Brand("02", "KAWASAKI"),
            1, curDateTime, curDateTime)
            );
        
        model.addAttribute("brands", brands);
        model.addAttribute("motos", motos);

        return "moto_list";
    }
}
