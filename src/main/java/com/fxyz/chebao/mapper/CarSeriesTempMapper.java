package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.carSpider.CarSeriesTemp;
import com.fxyz.chebao.pojo.carSpider.CarSeriesTempExample;
import java.util.List;

public interface CarSeriesTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarSeriesTemp record);

    int insertSelective(CarSeriesTemp record);

    List<CarSeriesTemp> selectByExample(CarSeriesTempExample example);

    CarSeriesTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarSeriesTemp record);

    int updateByPrimaryKey(CarSeriesTemp record);
}