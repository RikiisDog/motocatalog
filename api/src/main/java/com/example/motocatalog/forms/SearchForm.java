package com.example.motocatalog.forms;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SearchForm {

    private String brandId;
    
    @Size(min = 2, max = 10)
    private String keyword;
}
