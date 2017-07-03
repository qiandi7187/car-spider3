package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.carSpider.CarBrandTemp;

public interface CarBrandTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarBrandTemp record);

    int insertSelective(CarBrandTemp record);

    CarBrandTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarBrandTemp record);

    int updateByPrimaryKey(CarBrandTemp record);
}