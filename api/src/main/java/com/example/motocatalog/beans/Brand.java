package com.example.motocatalog.beans;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * バイクメーカー(ブランド)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @NotBlank(message = "{brand.brandId.NotBlank}")
    private String brandId;

    private String brandName;

}
