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
import com.example.motocatalog.forms.RegisterForm;
import com.example.motocatalog.forms.SearchForm;
import com.example.motocatalog.forms.UpdateForm;
import com.example.motocatalog.services.MotosService;
import com.example.motocatalog.services.exceptions.MotorcycleAlreadyExistsException;
import com.example.motocatalog.services.exceptions.MotorcycleDeleteFailedException;
import com.example.motocatalog.services.exceptions.MotorcycleDuplicateUpdateException;
import com.example.motocatalog.services.exceptions.MotorcycleRegistrationFailedException;

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
     * @param updateForm    入力内容
     * @param redirectAttrs リダイレクト先
     * @return 遷移先
     */
    @PostMapping("/motos/update")
    public String update(@Validated UpdateForm updateForm, BindingResult result,
            RedirectAttributes redirectAttrs, Model model) {

        // バリデーションエラー発生時
        if (result.hasErrors()) {
            // バイクメーカードロップダウン取得
            this.setBrands(model);
            // バイク情報を取得
            Motorcycle moto = service.getMotos(updateForm.getMotoNo());
            // 検索結果を入力内容に詰め替える
            BeanUtils.copyProperties(moto, updateForm);

            return "moto_update";
        }

        // 入力内容をインスタンスへ詰め替える
        Motorcycle moto = new Motorcycle();
        BeanUtils.copyProperties(updateForm, moto);

        try {
            // DBに保存する
            service.update(moto);
            return "redirect:/motos";

        } catch (OptimisticLockingFailureException | MotorcycleDuplicateUpdateException
                | MotorcycleAlreadyExistsException e) {
            // エラーメッセージをフラッシュスコープに保存
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/motos/" + updateForm.getMotoNo();
        }
    }

    /**
     * 削除ボタン押下後の処理
     * 
     * @param updateForm    入力内容
     * @param redirectAttrs リダイレクト先
     * @return 遷移先
     */
    @PostMapping("/motos/delete")
    public String delete(@ModelAttribute UpdateForm updateForm, RedirectAttributes redirectAttrs) {

        // 入力内容をインスタンスへ詰め替える
        Motorcycle moto = new Motorcycle();
        BeanUtils.copyProperties(updateForm, moto);

        try {
            // DBに保存する
            service.delete(moto.getMotoNo());
            return "redirect:/motos";

        } catch (MotorcycleDeleteFailedException e) {
            // エラーメッセージをフラッシュスコープに保存
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/motos/";
        }
    }

    /**
     * 登録画面の初期表示
     * 
     * @param motoNo     バイク番号
     * @param updateForm バイク情報
     * @param model      モデル
     * @return 遷移先
     */
    @GetMapping("motos/register")
    public String initRegister(@ModelAttribute("registerForm") RegisterForm registerForm, Model model) {

        // バイクメーカードロップダウン取得
        this.setBrands(model);

        return "moto_register";
    }

    /**
     * 登録ボタン押下後の処理
     * 
     * @param registerForm  入力内容
     * @param redirectAttrs リダイレクト先
     * @return 遷移先
     */
    @PostMapping("motos/register/success")
    public String register(@Validated @ModelAttribute RegisterForm registerForm, BindingResult result,
            RedirectAttributes redirectAttrs, Model model) {

        if (result.hasErrors()) {
            // バイクメーカードロップダウン取得
            this.setBrands(model);
            return "moto_register";
        }

        // 入力内容をインスタンスへ詰め替える
        Motorcycle moto = new Motorcycle();
        BeanUtils.copyProperties(registerForm, moto);

        try {
            // DBに保存する
            service.register(moto);
            return "redirect:/motos";

        } catch (MotorcycleRegistrationFailedException | MotorcycleAlreadyExistsException e) {
            // エラーメッセージをフラッシュスコープに保存
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            redirectAttrs.addFlashAttribute("registerForm", registerForm);
            return "redirect:/motos/register";
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
