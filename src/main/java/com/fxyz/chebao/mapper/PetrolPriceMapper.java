package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.petrol.PetrolPrice;
import com.fxyz.chebao.pojo.petrol.PetrolPriceExample;
import java.util.List;

public interface PetrolPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PetrolPrice record);

    int insertSelective(PetrolPrice record);

    List<PetrolPrice> selectByExample(PetrolPriceExample example);

    PetrolPrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PetrolPrice record);

    int updateByPrimaryKey(PetrolPrice record);
}