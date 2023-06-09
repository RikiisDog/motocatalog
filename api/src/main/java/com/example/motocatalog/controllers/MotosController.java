package com.example.motocatalog.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.motocatalog.beans.Brand;
import com.example.motocatalog.beans.Motorcycle;
import com.example.motocatalog.forms.SearchForm;
import com.example.motocatalog.services.MotosService;

@Controller
public class MotosController {

    @Autowired
    MotosService service;

    @RequestMapping("/")
    public String root() {
        return "redirect:/motos";
    }

    @GetMapping("/motos")
    public String motos(@Validated SearchForm searchForm, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "moto_list";
        }

        // バイクメーカードロップダウン取得
        this.setBrands(model);
        // バイク情報取得
        List<Motorcycle> motos = new ArrayList<>();
        motos = service.getMotos(searchForm);
        // Modelに詰める
        model.addAttribute("motos", motos);
        model.addAttribute("datetime", LocalDateTime.now());

        return "moto_list";
    }

    /**
     * キーワード初期化
     * 
     * @param searchForm
     * @return 遷移先
     */
    @GetMapping("motos/reset")
    public String resetKeyword(SearchForm searchForm, Model model) {

        // バイクメーカードロップダウン取得
        this.setBrands(model);
        // 検索フォーム初期化
        searchForm = new SearchForm();

        return "moto_list";
    }

    /**
     * バイクメーカードロップダウン取得
     * 
     * @param model
     */
    private void setBrands(Model model) {

        List<Brand> brands = new ArrayList<>();
        brands = service.getBrands();
        model.addAttribute("brands", brands);
    }
}
