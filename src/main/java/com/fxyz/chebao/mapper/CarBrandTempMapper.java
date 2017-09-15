package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.car.CarBrandTemp;
import com.fxyz.chebao.pojo.car.CarBrandTempExample;
import java.util.List;

public interface CarBrandTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarBrandTemp record);

    int insertSelective(CarBrandTemp record);

    List<CarBrandTemp> selectByExample(CarBrandTempExample example);

    CarBrandTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarBrandTemp record);

    int updateByPrimaryKey(CarBrandTemp record);
}