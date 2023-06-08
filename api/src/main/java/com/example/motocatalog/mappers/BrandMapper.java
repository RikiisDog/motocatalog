package com.example.motocatalog.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.motocatalog.beans.Brand;

@Mapper
public interface BrandMapper {
    /**
     * ブランド情報を全件取得する
     * 
     * @param: None
     * @return: None
     */
    public List<Brand> selectAll();
}
