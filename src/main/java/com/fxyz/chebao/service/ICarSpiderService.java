package com.fxyz.chebao.service;

import com.fxyz.chebao.pojo.Group;
import com.fxyz.chebao.pojo.carSpider.CarModelTemp;

import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */
public interface ICarSpiderService {
    /**
     * 根据首字母取得汽车品牌 厂商 车系
     * @param lettter
     */
    public void getCarBrandByCaption( String lettter);

    /**
     * 更新图片状态
     */
    public void getModelImgUrlById(int modelId);


        /**
         * 根据车系id取得停产车系的车型信息
         * @param modelId
         */
    public void getCarTypeStopModelById(int modelId);

    /**
     * 根据车系id取得在售车系停产车型的车型信息
     * @param modelId
     */
    public void getCarTypeStopSaleById(int modelId);

    /**
     * 根据车系id取得在售车系在售车型的车型信息
     * @param modelId
     */
    public void getCarTypeOnSaleById( int modelId );

    /**
     * 获取数据库表中所有的车系信息
     * @return
     */
    public List<CarModelTemp> getAllModels();


    }
