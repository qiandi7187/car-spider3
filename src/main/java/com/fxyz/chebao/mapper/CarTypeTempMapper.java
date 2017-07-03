package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.carSpider.CarTypeTemp;

public interface CarTypeTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarTypeTemp record);

    int insertSelective(CarTypeTemp record);

    CarTypeTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarTypeTemp record);

    int updateByPrimaryKey(CarTypeTemp record);
}