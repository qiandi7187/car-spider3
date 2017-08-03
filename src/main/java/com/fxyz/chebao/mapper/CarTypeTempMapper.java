package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.carSpider.CarTypeTemp;
import com.fxyz.chebao.pojo.carSpider.CarTypeTempExample;
import java.util.List;

public interface CarTypeTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarTypeTemp record);

    int insertSelective(CarTypeTemp record);

    List<CarTypeTemp> selectByExample(CarTypeTempExample example);

    CarTypeTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarTypeTemp record);

    int updateByPrimaryKey(CarTypeTemp record);
}