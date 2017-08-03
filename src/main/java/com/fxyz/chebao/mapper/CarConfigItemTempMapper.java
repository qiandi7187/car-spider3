package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.carSpider.CarConfigItemTemp;
import com.fxyz.chebao.pojo.carSpider.CarConfigItemTempExample;
import java.util.List;

public interface CarConfigItemTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarConfigItemTemp record);

    int insertSelective(CarConfigItemTemp record);

    List<CarConfigItemTemp> selectByExample(CarConfigItemTempExample example);

    CarConfigItemTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarConfigItemTemp record);

    int updateByPrimaryKey(CarConfigItemTemp record);
}