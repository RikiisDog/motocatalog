package com.example.motocatalog.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.motocatalog.beans.Brand;

@Mapper
public interface BrandMapper {
    public List<Brand> selectAll();
}
