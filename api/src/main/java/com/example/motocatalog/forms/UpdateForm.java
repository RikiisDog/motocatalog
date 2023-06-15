package com.example.motocatalog.forms;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.example.motocatalog.beans.Brand;

import lombok.Data;

/**
 * 更新画面に渡す用のForm
 */
@Data
public class UpdateForm {

    private int motoNo;

    @NotBlank
    @Size(min = 1, max = 50,message = "{updateForm.motoName.Size}")
    private String motoName;

    @Range(min = 0, max = 1000, message = "{updateForm.seatHeight.Range}")
    private Integer seatHeight;

    @Range(min = 1, max = 4, message = "{updateForm.cylinder.Range}")
    private Integer cylinder;

    @Size(max = 50, message = "{updateForm.cooling.Size}")
    private String cooling;

    @Range(min = 100000, message = "{updateForm.price.Range}")
    private Integer price;

    @Size(max = 200, message = "{updateForm.comment.Size}")
    private String comment;

    @Valid
    private Brand brand;

    private Integer version;

}
