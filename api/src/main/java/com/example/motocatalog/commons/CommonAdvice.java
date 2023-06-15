package com.example.motocatalog.commons;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * リクエストの空文字をNULLに変換する共通処理
 */
@ControllerAdvice
public class CommonAdvice {

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }
}
