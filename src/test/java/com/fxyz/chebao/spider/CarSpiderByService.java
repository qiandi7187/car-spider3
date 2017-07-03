package com.fxyz.chebao.spider;

import com.fxyz.chebao.mapper.CarModelTempMapper;
import com.fxyz.chebao.pojo.carSpider.CarModelTemp;
import com.fxyz.chebao.service.ICarSpiderService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qiandi on 2017/6/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring/applicationContext-*.xml")
public class CarSpiderByService {
    @Autowired
    ICarSpiderService carSpiderService;



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
    public void getModelUrl(){
        List<CarModelTemp> models = carSpiderService.getAllModels();
        System.out.println(models.size());
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
        for(CarModelTemp model:models){
            int modelId = model.getId();
            carSpiderService.getModelImgUrlById(modelId);
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
