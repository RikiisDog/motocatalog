package com.example.motocatalog.forms;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.example.motocatalog.beans.Brand;

import lombok.Data;

/**
 * 登録画面に渡す用のForm
 */
@Data
public class RegisterForm {

    private int motoNo;

    @NotBlank
    @Size(min = 1, max = 50, message = "{RegisterForm.motoName.Size}")
    private String motoName;

    @Range(min = 0, max = 1000, message = "{RegisterForm.seatHeight.Range}")
    private Integer seatHeight;

    @Range(min = 1, max = 4, message = "{RegisterForm.cylinder.Range}")
    private Integer cylinder;

    @Size(max = 50, message = "{RegisterForm.cooling.Size}")
    private String cooling;

    @Range(min = 100000, message = "{RegisterForm.price.Range}")
    private Integer price;

    @Size(max = 200, message = "{RegisterForm.comment.Size}")
    private String comment;

    @Valid
    private Brand brand;

    private Integer version;

}
