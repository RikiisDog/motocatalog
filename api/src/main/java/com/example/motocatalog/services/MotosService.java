package com.example.motocatalog.services;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    MessageSource messageSource;

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

    /**
     * バイク情報更新処理(例外があった場合ロールバックさせる)
     * 
     * @param moto
     * @return 更新件数
     */
    @Transactional
    public int save(Motorcycle moto) {
        int updateCount = motorcycleMapper.updateByMoto(moto);
        // 同時更新があった場合例外をスローする
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(
                    messageSource.getMessage("error.OptimisticLockingFailureException", null, Locale.JAPANESE));
        }
        // 更新後にリダイレクト処理をしているが2件以上の更新があった場合例外をスローする
        if (updateCount > 1) {
            throw new RuntimeException(
                    messageSource.getMessage("An unexpected error has occurred", null, Locale.JAPANESE));
        }
        return updateCount;
    }
}
