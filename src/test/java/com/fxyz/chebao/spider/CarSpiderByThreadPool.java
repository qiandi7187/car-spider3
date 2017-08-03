package com.fxyz.chebao.spider;

import com.fxyz.chebao.pojo.carSpider.CarSeriesTemp;
import com.fxyz.chebao.pojo.carSpider.CarSpiderErr;
import com.fxyz.chebao.pojo.carSpider.CarTypeTemp;
import com.fxyz.chebao.service.CarConfigSpiderService;
import com.fxyz.chebao.service.CarTypeSpiderService;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by qiandi on 2017/6/27.
 * 线程池方式读取信息
 * 该种存在并发问题，考虑最长20分钟可跑出结果，暂时启用该方法
 *
 * 2017/8/2  解决多线程并发问题，测试稳定，废弃单线程方法，启用多线程
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring/applicationContext-*.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarSpiderByThreadPool {
    @Autowired
    CarTypeSpiderService carTypeSpiderService;

    @Autowired
    CarConfigSpiderService carConfigSpiderService;

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
    public void a0001_getCarBrand(){
        for(int i=0;i<26;i++){
            final char caption = (char) (65 + i);
            carTypeSpiderService.getCarBrandByCaption(caption+"");
            System.out.println(caption+":结束");
        }
    }


    /**
     * 利用jsoup进行爬虫
     * 多线程  异步回调模式  按批次控制并发数量
     * 更新第三层车系的图片  +  获取第四层车型信息
     */
    @Test
    public void a0002_getSeriesUrlAndType(){
        List<CarSeriesTemp> Seriess = carTypeSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        int count = 0;
        //解决内存不释放问题  按批获取
        int batchSize = 100;
        for(int i=0;i<Seriess.size()/batchSize+1;i++){
            List<Future> list = new ArrayList<Future>();
            for(int j=0;j<batchSize && i*batchSize+j<Seriess.size();j++){
                    Integer seriesId = Seriess.get(i*batchSize+j).getId();
                    Future f = cachedThreadPool.submit( ()-> {
                        Map map=new HashMap();
                        Document doc=null;
                        try{
                            doc = carTypeSpiderService.sendSeriesImgUrlById(seriesId);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        map.put("doc",doc);
                        map.put("SeriesId",seriesId);
                        return map;
                    });
                    list.add(f);
            }
            System.out.println("总记："+(Seriess.size()/batchSize+1 )+ "   第"+(i+1)+"次    ");
            for(Future f : list) {
                try {
                    Map map=(Map) f.get(20000, TimeUnit.MILLISECONDS);
                    Document doc = (Document)map.get("doc");
                    Integer SeriesId=(Integer) map.get("SeriesId");
//                System.out.println("response:");
                    //获取在售车系的图片
                    carTypeSpiderService.deocdeSeriesImgUrlById(doc,SeriesId);
                    //在售车系 在售车型信息
                    carTypeSpiderService.decodeCarTypeOnSaleById(doc,SeriesId);
                    //获取在售车系 停售车型信息
                    carTypeSpiderService.decodeCarTypeStopSaleById(doc,SeriesId);
                    //获取停售车型信息
                    carTypeSpiderService.decodeCarTypeStopSeriesById(doc,SeriesId);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        cachedThreadPool.shutdown();
    }



    /**
     * 解析json
     * 多线程  异步回调模式  按批次控制并发数量
     *  获取车型的配置信息
     */
    @Test
    public void a0010_getTypeConfig(){
        List<CarTypeTemp> typeTemps = carTypeSpiderService.getAllTypeTemp();
        System.out.println(typeTemps.size());
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        int count = 0;
        //解决内存不释放问题  按批获取
        int batchSize = 100;
        //一次请求包含type的数量 最大为6
        int typelistSize = 6;
        //将typeTemps的id转化成每行batchSize 个typelist，每个typelist 含typelistSize个typeid 的集合  采用先new再add的方式
        List<List<StringBuffer>> rowlist = new ArrayList<List<StringBuffer>>();
        List<StringBuffer> row = null;
        StringBuffer idlist = null;
        for(int i=0;i<typeTemps.size();i++){
            if(i%(batchSize*typelistSize)==0){
                row = new ArrayList<StringBuffer>();
                rowlist.add(row);
            }
            if(i%typelistSize==0){
                idlist = new StringBuffer();
                row.add(idlist);
                idlist.append(typeTemps.get(i).getId());
            }else {
                idlist.append(","+typeTemps.get(i).getId());
            }
        }
        rowlist.forEach(row2->{System.out.println("***************************************");row2.forEach(idlist2-> System.out.println(idlist2.toString()));});
        int index = 0;
        for(List<StringBuffer> row3 : rowlist){
            List<Future> list = new ArrayList<Future>();
            for(StringBuffer idlist3 : row3){
                Future f = cachedThreadPool.submit( ()-> {
                    Map map=new HashMap();
                    String paramResult =null;
                    String configResult =null;
                    try{
                        paramResult = carConfigSpiderService.sendParamItemById(idlist3.toString());
                        configResult = carConfigSpiderService.sendConfigItemById(idlist3.toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    map.put("idlist",idlist3.toString());
                    map.put("paramResult",paramResult);
                    map.put("configResult",configResult);
                    return map;
                });
                list.add(f);
            }
            System.out.println("总记："+rowlist.size()+ "   第"+(index++)+"次    ");
            for(Future f : list) {
                try {
                    Map map=(Map) f.get(20000, TimeUnit.MILLISECONDS);
                    String  idlist4 = (String)map.get("idlist");
                    String  paramResult = (String)map.get("paramResult");
                    String  configResult = (String)map.get("configResult");
                    carConfigSpiderService.decodeParamItemById(paramResult,idlist4);
                    carConfigSpiderService.decodeConfigItemById(configResult,idlist4);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        carConfigSpiderService.insertConfigBatchFlush();
        cachedThreadPool.shutdown();

    }


    /**
     * 为configItem进行排序
     */
    @Test
    public void sortConfigItem(){
        carConfigSpiderService.sortConfigItem();
    }



    /**
     * 解析错误表中的ErrSpeclist 并重新获取车型配置信息
     *  同步单线程
     */
    @Test
    public void a0012_getTypeConfigErr(){
        List<CarSpiderErr> errlist = carConfigSpiderService.getErrSpeclist();
        for(CarSpiderErr err:errlist){
            if(err.getValue1().equals("paramtypeitems")){
                String configResult = carConfigSpiderService.sendConfigItemById(err.getValue0());
                carConfigSpiderService.decodeConfigItemById(configResult,err.getValue0());
            }
            if(err.getValue1().equals("configtypeitems")){
                String paramResult = carConfigSpiderService.sendParamItemById(err.getValue0());
                carConfigSpiderService.decodeParamItemById(paramResult,err.getValue0());
            }
        }
        carConfigSpiderService.insertConfigBatchFlush();
    }









}
