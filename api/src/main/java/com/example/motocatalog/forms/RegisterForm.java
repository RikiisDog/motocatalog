package com.example.motocatalog.forms;

import com.example.motocatalog.beans.Brand;

import lombok.Data;

/**
 * 更新画面に渡す用のForm
 */
@Data
public class RegisterForm {

    private int motoNo;
    private String motoName;
    private int seatHeight;
    private int cylinder;
    private String cooling;
    private int price;
    private String comment;
    private Brand brand;
    private int version;

}
