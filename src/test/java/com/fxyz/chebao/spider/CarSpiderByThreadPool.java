package com.fxyz.chebao.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxyz.chebao.mapper.CarBrandTempMapper;
import com.fxyz.chebao.mapper.CarManufacturerTempMapper;
import com.fxyz.chebao.mapper.CarModelTempMapper;
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

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    CarModelTempMapper modelTempMapper;

    /**
     * 取得汽车品牌 厂商 车系
     * 三层while循环嵌套 正则表达是匹配
     * 1 brand
     *  2 manufacturer
     *    3 model
     *
     *   三层对数据库现成开销较大 线程池较为保守
     */
    @Test
    public void getCarBrand(){
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
        Random rand = new Random();
        for(int i=0;i<=26;i++){
            final char caption = (char) (65 + i);
            try {
                //1-6秒
                Thread.sleep(rand.nextInt(5000)+1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    carSpiderService.getCarBrandByCaption(caption+"");
                    System.out.println(caption+":结束");
                }
            });



        }
    }

    /**
     * 获取在售车系的图片
     * 两种抓取方式 一种在售模板 一种停售模板
     */
    @Test
    public void getModelUrl(){
        List<CarModelTemp> models = carSpiderService.getAllModels();
        System.out.println(models.size());
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
        for(CarModelTemp model:models){
            final int modelId = model.getId();
           /* try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            cachedThreadPool.execute(new Runnable() {
                public void run() {

                    getModelImgUrlById(modelId);

                }
            });



        }

    }

    public void getModelImgUrlById(int modelId) {

        try {
            Document doc = Jsoup.connect("http://www.autohome.com.cn/" + modelId).get();
            //生成第三层车系图片信息
            String imgurl = doc.select(".autoseries-pic-img1 picture img").attr("src");
            System.out.println("第一种方式:"+imgurl);
            CarModelTemp model = new CarModelTemp();
            model.setId(modelId);
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                model.setImgurl(imgurl);
                modelTempMapper.updateByPrimaryKeySelective(model);
                return;
            }
            //没有取到则用第二种方式
            imgurl = doc.select(".models_info dt a img").attr("src");
            System.out.println("第一种未取到图片采用第二种方式:"+imgurl);
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                model.setImgurl(imgurl);
                modelTempMapper.updateByPrimaryKeySelective(model);
                return;
            }
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                throw new Exception("两种方式都没有获取图片");
            }
        } catch (Exception e) {
            System.out.println("生成第三层车系图片信息出错 model:" + modelId + "  " + e.getMessage());
        }

    }





    /**
     * 利用jsoup进行爬虫
     * 获取第四层 在售车系 在售车型信息
     */
    @Test
    public void getCarTypeOnSale(){
        List<CarModelTemp> models = carSpiderService.getAllModels();
        System.out.println(models.size());
        int i = 0;
        for(CarModelTemp model:models){
            carSpiderService.getCarTypeOnSaleById(model.getId());
            i++;
          // System.out.println(i+"    modelInterId:"+model.getInterId());
        }
    }

    /**
     * 获取在售车系 停售车型信息
     */
    @Test
    public void getCarTypeStopSale(){
        List<CarModelTemp> models = carSpiderService.getAllModels();
        System.out.println(models.size());
        for(CarModelTemp model:models){
            carSpiderService.getCarTypeStopSaleById(model.getId());
        }
    }

    /**
     * 获取停售车型信息
     */
    @Test
    public void getCarTypeStopModel(){
        List<CarModelTemp> models = carSpiderService.getAllModels();
        System.out.println(models.size());
        for(CarModelTemp model:models){
            carSpiderService.getCarTypeStopModelById(model.getId());
        }
    }



}
