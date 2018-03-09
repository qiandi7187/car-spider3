package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.car.NicknameSpider;
import com.fxyz.chebao.pojo.car.NicknameSpiderExample;
import java.util.List;

public interface NicknameSpiderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NicknameSpider record);

    int insertSelective(NicknameSpider record);

    List<NicknameSpider> selectByExample(NicknameSpiderExample example);

    NicknameSpider selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NicknameSpider record);

    int updateByPrimaryKey(NicknameSpider record);
}