package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.carSpider.CarSpiderErr;
import com.fxyz.chebao.pojo.carSpider.CarSpiderErrExample;
import java.util.List;

public interface CarSpiderErrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarSpiderErr record);

    int insertSelective(CarSpiderErr record);

    List<CarSpiderErr> selectByExample(CarSpiderErrExample example);

    CarSpiderErr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarSpiderErr record);

    int updateByPrimaryKey(CarSpiderErr record);
}