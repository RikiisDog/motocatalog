package com.example.motocatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motocatalog.beans.Brand;
import com.example.motocatalog.beans.Motorcycle;
import com.example.motocatalog.mappers.BrandMapper;
import com.example.motocatalog.mappers.MotorcycleMapper;

@Service
public class MotosService {

    @Autowired
    MotorcycleMapper motorcycleMapper;

    @Autowired
    BrandMapper brandMapper;

    public List<Motorcycle> getMotos() {
        return motorcycleMapper.selectAll();
    }

    public List<Brand> getBrands() {
        return brandMapper.selectAll();
    }
}
