package com.example.motocatalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.motocatalog.beans.Brand;
import com.example.motocatalog.beans.Motorcycle;

@SpringBootTest
public class MotosServiceTest {

    @Autowired
    MotosService service;

    @Test
    @DisplayName("DBの実件数とメソッドの戻り値の件数が同一であるか")
    void testSelectAll() {
        List<Motorcycle> motos = service.getMotos();
        assertEquals(2, motos.size());
    }

    @Test
    @DisplayName("setterが正常に動作するか")
    void testSetter() {
        List<Motorcycle> motosList = service.getMotos();
        motosList.get(0).setUpdDt(null);
        motosList.get(0).setInsDt(null);
        Motorcycle moto = new Motorcycle();
        moto.setMotoNo(1);
        moto.setMotoName("GB350");
        moto.setSeatHeight(800);
        moto.setCylinder(1);
        moto.setCooling("空冷");
        moto.setPrice(550000);
        moto.setComment("エンジン音が素晴らしい");
        moto.setBrand(new Brand("01", "Honda"));
        moto.setVersion(1);
        moto.setInsDt(null);
        moto.setUpdDt(null);
        assertEquals(moto, motosList.get(0));
    }

    @Test
    @DisplayName("getterが正常に動作するか")
    void testGetter() {
        List<Motorcycle> motosList = service.getMotos();
        Motorcycle motos = motosList.get(0);
        assertEquals(motos.getMotoNo(), 1);
        assertEquals(motos.getMotoName(), "GB350");
        assertEquals(motos.getSeatHeight(), 800);
        assertEquals(motos.getCylinder(), 1);
        assertEquals(motos.getCooling(), "空冷");
        assertEquals(motos.getPrice(), 550000);
        assertEquals(motos.getComment(), "エンジン音が素晴らしい");
        assertEquals(motos.getBrand().getBrandId(), "01");
        assertEquals(motos.getBrand().getBrandName(), "Honda");
        assertEquals(motos.getVersion(), 1);
    }
}
