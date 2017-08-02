package com.fxyz.chebao.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxyz.chebao.mapper.CarBrandTempMapper;
import com.fxyz.chebao.mapper.CarManufacturerTempMapper;
import com.fxyz.chebao.mapper.CarSeriesTempMapper;
import com.fxyz.chebao.mapper.CarTypeTempMapper;
import com.fxyz.chebao.pojo.carSpider.*;
import com.fxyz.chebao.service.ICarSpiderService;
import com.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qiandi on 2017/6/27.
 * 线程池方式读取信息
 * 该种存在并发问题，考虑最长20分钟可跑出结果，暂时启用该方法
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring/applicationContext-*.xml")
public class CarSpiderByThreadPool {
    @Autowired
    ICarSpiderService carSpiderService;

    @Autowired
    CarSeriesTempMapper seriesTempMapper;

    @Autowired
    CarTypeTempMapper typeTempMapper;

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
     * 获取在售车系的图片  +  利用jsoup进行爬虫获取第四层
     *
     */
    @Test
    public void getSeriesUrl(){
        List<CarSeriesTemp> Seriess = carSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);
        List<Future> list = new ArrayList<Future>();
        int count = 0;
        for (CarSeriesTemp series : Seriess){
            final int SeriesId = series.getId();
            Future f = cachedThreadPool.submit( ()-> {
                Map map=new HashMap();
                Document doc=null;
                try{
                     doc = carSpiderService.sendSeriesImgUrlById(SeriesId);
                }catch(Exception e){
                    e.printStackTrace();
                }
                map.put("doc",doc);
                map.put("SeriesId",SeriesId);
                return map;
            });
            list.add(f);
        }
        cachedThreadPool.shutdown();
        for(Future f : list) {
            try {
                Map map=(Map) f.get(5000, TimeUnit.MILLISECONDS);
                Document doc = (Document)map.get("doc");
                Integer SeriesId=(Integer) map.get("SeriesId");
//                System.out.println("response:");
                //获取在售车系的图片
                carSpiderService.deocdeSeriesImgUrlById(doc,SeriesId);
                //在售车系 在售车型信息
                carSpiderService.decodeCarTypeOnSaleById(doc,SeriesId);
                //获取在售车系 停售车型信息
                carSpiderService.decodeCarTypeStopSaleById(doc,SeriesId);
                //获取停售车型信息
                carSpiderService.decodeCarTypeStopSeriesById(doc,SeriesId);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
