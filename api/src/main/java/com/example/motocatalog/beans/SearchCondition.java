package com.example.motocatalog.beans;

import lombok.Data;

/**
 * 検索条件
 */
@Data
public class SearchCondition {

    private String brandId;
    private String keyword;
}
