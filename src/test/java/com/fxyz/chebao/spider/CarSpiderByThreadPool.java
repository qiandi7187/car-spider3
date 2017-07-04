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

    /**
     * 取得汽车品牌 厂商 车系
     * 三层while循环嵌套 正则表达是匹配
     * 1 brand
     *  2 manufacturer
     *    3 Series
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
    public void getSeriesUrl(){
        List<CarSeriesTemp> Seriess = carSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
        for(CarSeriesTemp Series:Seriess){
            final int SeriesId = Series.getId();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    getSeriesImgUrlById(SeriesId);
                }
            });
        }
        cachedThreadPool.shutdown();
    }

    /**
     * 获取在售车系的图片
     * 两种抓取方式 一种在售模板 一种停售模板
     */
    @Test
    public void getSeriesUrl2(){
        List<CarSeriesTemp> Seriess = carSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        for(CarSeriesTemp Series:Seriess){
            final int SeriesId = Series.getId();


                 try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

           new Thread(){
               public void run() {
                   getSeriesImgUrlById(SeriesId);
               }
           }.start();


        }

    }

    /**
     * 获取在售车系的图片
     * 两种抓取方式 一种在售模板 一种停售模板
     */
    @Test
    public void getSeriesUrl3(){
        List<CarSeriesTemp> Seriess = carSpiderService.getAllSeriesTemp();
        System.out.println(Seriess.size());
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
        List<Future> list = new ArrayList<Future>();
        for (CarSeriesTemp series : Seriess){
            final int SeriesId = series.getId();
            Future f = cachedThreadPool.submit( ()-> {
                Map map=new HashMap();
                Document doc=null;
                try{
                     doc=Jsoup.connect("http://www.autohome.com.cn/" + SeriesId).get();
                }catch(Exception e){
                    e.printStackTrace();
                }
                map.put("doc",doc);
                map.put("SeriesId",SeriesId);
                return map;
            });
          /*  Future f = cachedThreadPool.submit(
                    new Callable() {
                       @Override
                       public Document call() throws Exception {
                           //Thread.sleep(new Random().nextInt(3000));
                           Document doc = Jsoup.connect("http://www.autohome.com.cn/" + SeriesId).get();
                           return doc;
                       }
                   }
            );*/


            list.add(f);
        }
        cachedThreadPool.shutdown();
        for(Future f : list) {
            try {
                Map map=(Map) f.get(5000, TimeUnit.MILLISECONDS);
                Document doc = (Document)map.get("doc");
                Integer SeriesId=(Integer) map.get("SeriesId");
//                System.out.println("response:");
                suruiliang(doc,SeriesId);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void suruiliang(Document doc,Integer SeriesId ) {

        try {
          //  Document doc = Jsoup.parse(response);
            //生成第三层车系图片信息
            String imgurl = doc.select(".autoseries-pic-img1 picture img").attr("src");
            System.out.println("SeriesId:"+SeriesId);
            System.out.println("第一种方式huj:"+imgurl);
            CarSeriesTemp Series = new CarSeriesTemp();
            Series.setId(SeriesId);
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                Series.setImgurl(imgurl);
                int i=seriesTempMapper.updateByPrimaryKeySelective(Series);
                System.out.println("i="+i);
                return;
            }
            //没有取到则用第二种方式
            imgurl = doc.select(".Seriess_info dt a img").attr("src");
            System.out.println("第一种未取到图片采用第二种方式:"+imgurl);
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                Series.setImgurl(imgurl);
                int i=seriesTempMapper.updateByPrimaryKeySelective(Series);
                System.out.println("i2="+i);
                return;
            }
            if(imgurl==null||(imgurl.indexOf("http")==-1)){
                throw new Exception("两种方式都没有获取图片");
            }
        } catch (Exception e) {
            System.out.println("生成第三层车系图片信息出错 "  + "  " + e.getMessage());
        }

    }






    public void getSeriesImgUrlById(int SeriesId) {

        try {
            Document doc = Jsoup.connect("http://www.autohome.com.cn/" + SeriesId).get();
            System.out.println(SeriesId);
            //生成第三层车系图片信息
            String imgurl = doc.select(".autoseries-pic-img1 picture img").attr("src");
            System.out.println("第一种方式:"+imgurl);
            CarSeriesTemp Series = new CarSeriesTemp();
            Series.setId(SeriesId);
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                Series.setImgurl(imgurl);
                seriesTempMapper.updateByPrimaryKeySelective(Series);
                return;
            }
            //没有取到则用第二种方式
            imgurl = doc.select(".Seriess_info dt a img").attr("src");
            System.out.println("第一种未取到图片采用第二种方式:"+imgurl);
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                Series.setImgurl(imgurl);
                seriesTempMapper.updateByPrimaryKeySelective(Series);
                return;
            }
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                throw new Exception("两种方式都没有获取图片");
            }
        } catch (Exception e) {
            System.out.println("生成第三层车系图片信息出错 Series:" + SeriesId + "  " + e.getMessage());
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



}
