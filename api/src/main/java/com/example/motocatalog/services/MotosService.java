package com.example.motocatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motocatalog.beans.Brand;
import com.example.motocatalog.beans.Motorcycle;
import com.example.motocatalog.beans.SearchCondition;
import com.example.motocatalog.forms.SearchForm;
import com.example.motocatalog.mappers.BrandMapper;
import com.example.motocatalog.mappers.MotorcycleMapper;

@Service
public class MotosService {

    @Autowired
    MotorcycleMapper motorcycleMapper;

    @Autowired
    BrandMapper brandMapper;

    public List<Motorcycle> getMotos(SearchForm form) {
        return motorcycleMapper.selectByCondition(form);
    }

    public List<Motorcycle> getMotos(SearchCondition condition) {
        return motorcycleMapper.selectByCondition(condition);
    }

    public Motorcycle getMotos(int motoNo) {
        return motorcycleMapper.selectByPK(motoNo);
    }

    public List<Brand> getBrands() {
        return brandMapper.selectAll();
    }

    public int save(Motorcycle moto) {
        return motorcycleMapper.updateByMoto(moto);
    }
}
