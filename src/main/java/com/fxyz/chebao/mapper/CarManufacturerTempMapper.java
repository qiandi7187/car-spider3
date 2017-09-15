package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.car.CarManufacturerTemp;

public interface CarManufacturerTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarManufacturerTemp record);

    int insertSelective(CarManufacturerTemp record);

    CarManufacturerTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarManufacturerTemp record);

    int updateByPrimaryKey(CarManufacturerTemp record);
}