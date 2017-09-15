package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.car.CarSpiderErr;
import com.fxyz.chebao.pojo.car.CarSpiderErrExample;
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