package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.car.CarConfigTemp;

import java.util.List;

public interface CarConfigTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarConfigTemp record);

    int insertBatch(List<CarConfigTemp> list);

    int insertSelective(CarConfigTemp record);

    CarConfigTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarConfigTemp record);

    int updateByPrimaryKey(CarConfigTemp record);
}