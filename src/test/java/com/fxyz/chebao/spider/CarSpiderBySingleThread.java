package com.fxyz.chebao.spider;

import com.fxyz.chebao.mapper.CarSeriesTempMapper;
import com.fxyz.chebao.pojo.carSpider.CarSeries;
import com.fxyz.chebao.pojo.carSpider.CarSeriesTemp;
import com.fxyz.chebao.service.ICarSpiderService;
import com.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qiandi on 2017/6/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring/applicationContext-*.xml")
public class CarSpiderBySingleThread {
    @Autowired
    ICarSpiderService carSpiderService;


    /**
     * 取得汽车品牌 厂商 车系
     * 三层while循环嵌套 正则表达是匹配
     * 1 brand
     *  2 manufacturer
     *    3 Series
     */
    @Test
    public void getCarBrand(){
        for(int i=0;i<26;i++){
            final char caption = (char) (65 + i);
            try {
                //0.5秒
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            carSpiderService.getCarBrandByCaption(caption+"");
            System.out.println(caption+":结束");
        }
    }

    /**
     * 获取在售车系的图片
     * 两种抓取方式 一种在售模板 一种停售模板
     */
    @Test
    public void getSeriesUrl(){
        List<CarSeriesTemp> Seriess = carSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        for(CarSeriesTemp Series:Seriess){
            int SeriesId = Series.getId();
            carSpiderService.getSeriesImgUrlById(SeriesId);
        }
    }


    /**
     * 利用jsoup进行爬虫
     * 获取第四层 在售车系 在售车型信息
     */
    @Test
    public void getCarTypeOnSale(){
        List<CarSeriesTemp> Seriess = carSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        int i = 0;
        for(CarSeriesTemp Series:Seriess){
            carSpiderService.getCarTypeOnSaleById(Series.getId());
            i++;
          // System.out.println(i+"    SeriesInterId:"+Series.getInterId());
        }
    }

    /**
     * 获取在售车系 停售车型信息
     */
    @Test
    public void getCarTypeStopSale(){
        List<CarSeriesTemp> Seriess = carSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        for(CarSeriesTemp Series:Seriess){
            carSpiderService.getCarTypeStopSaleById(Series.getId());
        }
    }

    /**
     * 获取停售车型信息
     */
    @Test
    public void getCarTypeStopSeries(){
        List<CarSeriesTemp> Seriess = carSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        for(CarSeriesTemp Series:Seriess){
            carSpiderService.getCarTypeStopSeriesById(Series.getId());
        }
    }


    /**
     * 在数据从临时表导入正式表后
     * 根据SeriesID从数据库获取图片地址 并将图片保存到本地
     */
    @Test
    public void copySeriesUrl(){
        List<CarSeries> Seriess = carSpiderService.getAllSeries();
        System.out.println(Seriess.size());
        int index = 0;
        for(CarSeries Series:Seriess){
            carSpiderService.copySeriesUrlById("D:\\imgs",Series.getId()+".jpg",Series.getImgurl());
            System.out.println(index++);
        }
    }



}
