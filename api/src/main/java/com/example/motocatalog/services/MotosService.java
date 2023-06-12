package com.example.motocatalog.services;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.motocatalog.beans.Brand;
import com.example.motocatalog.beans.Motorcycle;
import com.example.motocatalog.beans.SearchCondition;
import com.example.motocatalog.forms.SearchForm;
import com.example.motocatalog.mappers.BrandMapper;
import com.example.motocatalog.mappers.MotorcycleMapper;
import com.example.motocatalog.services.exceptions.MotorcycleAlreadyExistsException;
import com.example.motocatalog.services.exceptions.MotorcycleDeleteFailedException;
import com.example.motocatalog.services.exceptions.MotorcycleDuplicateUpdateException;
import com.example.motocatalog.services.exceptions.MotorcycleRegistrationFailedException;

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
     * @param moto バイク情報
     * @return 更新件数
     */
    @Transactional
    public int update(Motorcycle moto) {
        try {
            int updateCount = motorcycleMapper.updateByMoto(moto);

            // 同時更新があった場合例外をスローする
            if (updateCount == 0) {
                throw new OptimisticLockingFailureException(
                        messageSource.getMessage(
                                "error.OptimisticLockingFailureException", null, Locale.JAPANESE));
            }

            // 更新後にリダイレクト処理をしているが2件以上の更新があった場合例外をスローする
            if (updateCount > 1) {
                throw new MotorcycleDuplicateUpdateException(
                        messageSource.getMessage(
                                "error.MotorcycleDuplicateUpdateException", null, Locale.JAPANESE));
            }
            return updateCount;

        } catch (DataIntegrityViolationException e) {
            // 同名のバイクを登録した場合、ユニーク制約違反例外スローを投げる
            throw new MotorcycleAlreadyExistsException(
                    messageSource.getMessage(
                            "error.MotorcycleAlreadyExistsException", null, Locale.JAPANESE));
        }
    }

    /**
     * バイク情報新規登録処理(例外があった場合ロールバックさせる)
     * 
     * @param moto バイク情報
     * @return 登録件数
     */
    @Transactional
    public int register(Motorcycle moto) {
        try {
            int registerCount = motorcycleMapper.insert(moto);
            // 登録できなかった場合例外をスローする
            if (registerCount == 0) {
                throw new MotorcycleRegistrationFailedException(
                        messageSource.getMessage(
                                "error.MotorcycleRegistrationFailedException", null, Locale.JAPANESE));
            }
            return registerCount;

        } catch (DataIntegrityViolationException e) {
            // 同名のバイクを登録した場合、ユニーク制約違反例外スローを投げる
            throw new MotorcycleAlreadyExistsException(
                    messageSource.getMessage(
                            "error.MotorcycleAlreadyExistsException", null, Locale.JAPANESE));
        }
    }

    /**
     * バイク情報削除処理(例外があった倍ロークバックさせる)
     * 
     * @param motoNo バイク番号
     * @return 削除件数
     */
    @Transactional
    public int delete(int motoNo) {
        int deleteCount = motorcycleMapper.deleteByPK(motoNo);
        // 削除できなかった場合例外をスローする
        if (deleteCount == 0) {
            throw new MotorcycleDeleteFailedException(
                    messageSource.getMessage(
                            "error.MotorcycleDeleteFailedException", null, Locale.JAPANESE));
        }
        return deleteCount;
    }
}
