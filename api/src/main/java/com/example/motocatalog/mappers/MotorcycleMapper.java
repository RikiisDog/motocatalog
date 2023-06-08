package com.example.motocatalog.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.motocatalog.beans.Motorcycle;
import com.example.motocatalog.beans.SearchCondition;

@Mapper
public interface MotorcycleMapper {

    /**
     * バイク情報を検索条件で検索する
     * 
     * @param: 検索条件
     * @return: バイク情報リスト
     */
    public List<Motorcycle> selectByCondition(SearchCondition condition);

    /**
     * バイク情報を検索条件で検索する
     * 
     * @param: バイク番号
     * @return: バイク情報
     */
    public Motorcycle selectByPK(int motoNo);

    /**
     * バイク情報を更新する
     * 
     * @param: バイク情報
     * @return: 更新件数
     */
    public int updateByMoto(Motorcycle moto);
}
