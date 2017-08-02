package com.fxyz.chebao.spider;

import com.fxyz.chebao.mapper.CarSeriesTempMapper;
import com.fxyz.chebao.mapper.CarTypeTempMapper;
import com.fxyz.chebao.pojo.carSpider.CarSeriesTemp;
import com.fxyz.chebao.service.ICarSpiderService;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

    private static Logger logger = Logger.getLogger(CarSpiderByThreadPool.class);


    @Before
    public void loadConfig(){
        try {
            Log4jConfigurer.initLogging("classpath:config/property/log4j.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        int count = 0;
        int batchSize = 100;//解决内存不释放问题  按批释放
        for(int i=0;i<Seriess.size()/batchSize+1;i++){
            List<Future> list = new ArrayList<Future>();
            for(int j=0;j<batchSize && i*batchSize+j<Seriess.size();j++){
                    final int SeriesId = Seriess.get(i*batchSize+j).getId();
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
            System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS 总："+(Seriess.size()/batchSize+1 )+ "   第"+(i+1)+"次    ");
            for(Future f : list) {
                try {
                    Map map=(Map) f.get(20000, TimeUnit.MILLISECONDS);
                    Document doc = (Document)map.get("doc");
                    Integer SeriesId=(Integer) map.get("SeriesId");
//                System.out.println("response:");
                    //获取在售车系的图片
                    //carSpiderService.deocdeSeriesImgUrlById(doc,SeriesId);
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
        cachedThreadPool.shutdown();
    }




}
