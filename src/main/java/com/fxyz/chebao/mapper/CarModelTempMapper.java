package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.carSpider.CarModelTemp;
import com.fxyz.chebao.pojo.carSpider.CarModelTempExample;
import java.util.List;

public interface CarModelTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarModelTemp record);

    int insertSelective(CarModelTemp record);

    List<CarModelTemp> selectByExample(CarModelTempExample example);

    CarModelTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarModelTemp record);

    int updateByPrimaryKey(CarModelTemp record);
}