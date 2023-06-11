package com.example.motocatalog.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.motocatalog.beans.Brand;
import com.example.motocatalog.beans.Motorcycle;
import com.example.motocatalog.forms.SearchForm;
import com.example.motocatalog.forms.UpdateForm;
import com.example.motocatalog.services.MotosService;

@Controller
public class MotosController {

    @Autowired
    MotosService service;

    @RequestMapping("/")
    public String root() {
        return "redirect:/motos";
    }

    /**
     * 一覧画面の初期表示
     * 
     * @param searchForm 全てのバイク情報
     * @param result     バリデーション結果
     * @param model      モデル
     * @return 遷移先
     */
    @GetMapping("/motos")
    public String initMotos(@Validated SearchForm searchForm, BindingResult result, Model model) {

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
     * @param searchForm 検索キーワード
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
     * 更新画面の初期表示
     * 
     * @param motoNo     バイク番号
     * @param updateForm バイク情報
     * @param model      モデル
     * @return 遷移先
     */
    @GetMapping("motos/{motoNo}")
    public String initUpdate(@PathVariable("motoNo") int motoNo,
            @ModelAttribute UpdateForm updateForm,
            Model model) {

        // バイクメーカードロップダウン取得
        this.setBrands(model);
        // バイク情報を取得
        Motorcycle moto = service.getMotos(motoNo);
        // 検索結果を入力内容に詰め替える
        BeanUtils.copyProperties(moto, updateForm);

        return "moto_update";
    }

    /**
     * 更新ボタン押下後の処理
     * 
     * @return 遷移先
     */
    @PostMapping("/motos/save")
    public String save(@ModelAttribute UpdateForm updateForm, RedirectAttributes redirectAttrs) {

        // 入力内容をインスタンスへ詰め替える
        Motorcycle moto = new Motorcycle();
        BeanUtils.copyProperties(updateForm, moto);

        try {
            // DBに保存する
            service.save(moto);
            return "redirect:/motos";

        } catch (OptimisticLockingFailureException e) {
            // エラーメッセージをフラッシュスコープに保存
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/motos/" + updateForm.getMotoNo();
        }
    }

    /**
     * バイクメーカードロップダウン取得
     * 
     * @param model モデル
     */
    private void setBrands(Model model) {

        List<Brand> brands = new ArrayList<>();
        brands = service.getBrands();
        model.addAttribute("brands", brands);
    }
}
