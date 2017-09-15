package com.fxyz.chebao.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxyz.chebao.mapper.*;
import com.fxyz.chebao.pojo.car.*;
import com.util.HttpUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CarTypeSpiderService {
    private static Logger logger = Logger.getLogger(CarTypeSpiderService.class);

    @Autowired
    CarBrandTempMapper brandTempMapper;
    @Autowired
    CarManufacturerTempMapper manufacturerTempMapper;
    @Autowired
    CarSeriesTempMapper seriesTempMapper;
    @Autowired
    CarTypeTempMapper typeTempMapper;
    @Autowired
    CarSeriesMapper carSeriesMapper;



    //将发送和接收拆分以支持异步模式

    public void getSeriesImgUrlById(int seriesId) {
        try {
            Document doc = sendSeriesImgUrlById(seriesId);
            //生成第三层车系图片信息
            deocdeSeriesImgUrlById(doc,seriesId);
        } catch (Exception e) {
            logger.error("生成第三层车系图片信息出错 Series:" + seriesId + "  " + e.getMessage());
        }

    }

    public Document sendSeriesImgUrlById(int seriesId) throws IOException {
        return Jsoup.connect("http://www.autohome.com.cn/" + seriesId).get();
    }

    public void deocdeSeriesImgUrlById(Document doc,Integer seriesId ) {
        try {
            //  Document doc = Jsoup.parse(response);
            //生成第三层车系图片信息
            String imgurl = doc.select(".autoseries-pic-img1 picture img").attr("src");
            logger.info("SeriesId:"+seriesId);
            logger.info("第一种方式huj:"+imgurl);
            CarSeriesTemp series = new CarSeriesTemp();
            series.setId(seriesId);
            if(imgurl!=null&& (imgurl.indexOf("//")!=-1)){
                if(imgurl.indexOf("http")==-1){
                    series.setImgurl("http:"+imgurl);
                }else{
                    series.setImgurl(imgurl);
                }
                seriesTempMapper.updateByPrimaryKeySelective(series);
                return;
            }
            //没有取到则用第二种方式
            imgurl = doc.select(".models_info dt a img").attr("src");
            logger.info("第一种未取到图片采用第二种方式:"+imgurl);
            if(imgurl!=null&& (imgurl.indexOf("http")!=-1)){
                series.setImgurl(imgurl);
                seriesTempMapper.updateByPrimaryKeySelective(series);
                return;
            }
            if(series.getImgurl()==null || (series.getImgurl().indexOf("http")==-1)){
                throw new Exception("两种方式都没有获取图片");
            }
        } catch (Exception e) {
            logger.error("生成第三层车系图片信息出错 "  + "  " + e.getMessage());
        }

    }

    public void getCarTypeStopSaleById(int seriesId){
        //int SeriesInterId = 19;
        try {
            Document doc = sendCarType(seriesId);
            decodeCarTypeStopSaleById(doc,seriesId);
        } catch (Exception e) {
            logger.error("生成停售车型出错："+e.getMessage());
        }

    }


    public Document sendCarType(int SeriesId) throws IOException {
        return Jsoup.connect("http://www.autohome.com.cn/"+ SeriesId).get();
    }

    public void decodeCarTypeStopSaleById(Document doc,Integer seriesId) throws Exception {
        Elements as = doc.select("#drop2 ul li a");
        for (Element a : as) {
            String yid = a.attr("data");
            //System.out.println(yid);
            String response = HttpUtil.sendGet("http://www.autohome.com.cn/ashx/series_allspec.ashx?s=" + seriesId + "&y=" + yid + "&l=3");
            JSONObject resJson = JSONObject.parseObject(response);
            JSONArray specArr = resJson.getJSONArray("Spec");

            for (int i = 0; i < specArr.size(); i++) {
                CarTypeTemp type = new CarTypeTemp();
                JSONObject typeJson = specArr.getJSONObject(i);
                //System.out.println(typeJson);
                type.setOrl(i*10+10);
                try {
                    type.setId(typeJson.getIntValue("Id"));
                }catch (Exception e){
                    logger.error("获取id失败");
                }
                type.setSeriesId(seriesId);
                type.setDrivingMode(typeJson.getString("DrivingModeName"));
                type.setTransmission(typeJson.getString("Transmission"));
                type.setGuidePrice(typeJson.getString("Price"));
                type.setSecondPrice(typeJson.getString("Price2Sc"));
                type.setName(typeJson.getString("Name"));
                type.setGroupName(typeJson.getString("GroupName"));
                type.setTax(typeJson.getBoolean("ShowTaxRelief")==true?"减税":null);
                type.setState("停售");
                logger.info("type  id:"+type.getId()+"   name:"+type.getName()+"  guideprice: "+type.getGuidePrice()+"  dealerPrice:"+type.getDealerPrice()+
                        "   DrivingMode:"+type.getDrivingMode()+"   setTransmission:"+type.getTransmission());
                typeTempMapper.insertSelective(type);
            }
        }
    }

    public void getCarTypeOnSaleById( int seriesId ){
        //int SeriesInterId = 19;
        try{
            Document doc = sendCarType(seriesId );
            decodeCarTypeOnSaleById(doc,seriesId);
        } catch (Exception e) {
            logger.error("生成在售车型出错："+e.getMessage());
        }

    }

    public void decodeCarTypeOnSaleById(Document doc,int seriesId){
        //生成第四层信息
        Elements lis = doc.select("#speclist .current ul li");
        int orl = 10;
        for (Element li : lis) {
            String liStr = li.html();
            // System.out.println("--------------------------------------------------");
            // System.out.println(liStr);
            CarTypeTemp type = new CarTypeTemp();
            type.setSeriesId(seriesId);
            type.setOrl(orl);
            try{
                String href = li.select(".interval01-list-cars-infor p:eq(0) a").attr("href");
                Pattern p = Pattern.compile("/spec/(.*?)/#pvareaid.*?");
                Matcher m = p.matcher(href);
                if(m.matches()) {
                    type.setId(Integer.parseInt( m.group(1)));
                }
            }catch (Exception e){
                logger.error("抓取id出错");
            }
            type.setName(li.select(".interval01-list-cars-infor p:eq(0) a").text());
            String p2 = li.select(".interval01-list-cars-infor p:eq(1)").html();
            if(p2.indexOf("停产在售")!=-1){
                type.setState("停产在售");
            }else{
                type.setState("在售");
            }
            if(p2.indexOf("减税")!=-1){
                type.setTax("减税");
            }
            //处理多描述的情况
            Elements spans = li.select(".interval01-list-cars-infor p:eq(2) span");
            StringBuffer drivingMode = new StringBuffer();
            for(int i=0;i<spans.size()-1;i++){
                drivingMode.append((i==0?"":"/")+spans.get(i).text());
            }
            type.setDrivingMode(drivingMode.toString());
            type.setTransmission(li.select(".interval01-list-cars-infor p:eq(2) span:last-child").text());
            type.setGroupName(li.parent().previousElementSibling().select(".interval01-list-cars-text").text());
            type.setGuidePrice(li.select(".interval01-list-guidance div").text());

            logger.info("type  id:"+type.getId()+"   name:"+type.getName()+"  guideprice: "+type.getGuidePrice()+"  dealerPrice:"+type.getDealerPrice()+
                    "   DrivingMode:"+type.getDrivingMode()+"   setTransmission:"+type.getTransmission());
            typeTempMapper.insertSelective(type);
            orl += 10;
        }
    }

    public void getCarTypeStopSeriesById(int seriesId){
        try{
            Document doc = sendCarType(seriesId );
            decodeCarTypeStopSeriesById(doc,seriesId);
        } catch (Exception e) {
            logger.error("生成停售车车系的车型出错："+e.getMessage());
        }
    }


    public void decodeCarTypeStopSeriesById(Document doc,int seriesId){
        //int SeriesInterId = 19;
        try {
            Elements trs = doc.select(".models_tab tr");
            int orl = 10;
            for(Element tr:trs){
                CarTypeTemp type = new CarTypeTemp();
                type.setOrl(orl);
                type.setState("停售");
                type.setGuidePrice(tr.select(".price_d").first().text());
                try{
                    String href = tr.select(".name_d a").attr("href");
                    Pattern p = Pattern.compile("spec/(.*?)/");
                    Matcher m = p.matcher(href);
                    if(m.matches()) {
                        type.setId(Integer.parseInt( m.group(1)));
                    }

                }catch (Exception e){
                    logger.error("抓取id出错");
                }
                type.setSeriesId(seriesId);
                type.setName(tr.select(".name_d a").text());
                type.setSecondPrice(tr.select(".price_d span a").text());
                logger.info("type  id:"+type.getId()+"   name:"+type.getName()+"  guideprice: "+type.getGuidePrice()+"  dealerPrice:"+type.getDealerPrice()+
                        "   SecondPrice:"+type.getSecondPrice()+"   setTransmission:"+type.getTransmission());
                typeTempMapper.insertSelective(type);
                orl+=10;
            }

        } catch (Exception e) {
            logger.error("生成停售车系的车型出错："+e.getMessage());
        }
    }


    public void getCarBrandByCaption( String lettter){
        //String lettter = "A";
        try {
            String response = HttpUtil.sendGet("http://www.autohome.com.cn/grade/carhtml/"+ lettter +".html","gb2312");
            //System.out.println(response);
            //group1 <dt>  group2 <dd>  一层表达式
            Pattern pattern = Pattern.compile("<dl.*?(<dt.*?</dt>).*?(<dd.*?</dd>).*?</dl>",Pattern.DOTALL); // 正则表达式   可以匹配任何字符，包括行结束符
            Matcher matcher = pattern.matcher(response); // 操作的字符串
            while (matcher.find()) {
                CarBrandTemp brand = new CarBrandTemp();
                //System.out.println("总："+matcher.group(0));
                //获取品牌id orl 二层表达式
                Pattern patternChild = Pattern.compile("<dl.*?id=\"(.*?)\".*?olr=\"(\\S+)\".*?>",Pattern.DOTALL);
                Matcher matcherChild = patternChild.matcher(matcher.group(0));
                while (matcherChild.find()) {
                    logger.info("品牌id：" + matcherChild.group(1)+"    olr:"+matcherChild.group(2));
                    try {
                        brand.setId(Integer.parseInt(matcherChild.group(1)));
                    }catch (Exception e){
                        logger.error("获取ID失败");
                    }

                    brand.setOrl(matcherChild.group(2));
                    brand.setLetter(lettter);
                }
                //System.out.println("品牌："+matcher.group(1));
                //获取品牌名称
                patternChild = Pattern.compile("<dt.*?<img.*?src=\"(.*?)\">.*?<div><a.*?>(.*?)</a>.*?</dt>",Pattern.DOTALL);
                matcherChild = patternChild.matcher(matcher.group(1));
                while (matcherChild.find()) {
                    logger.info("品牌名称：" + matcherChild.group(2)+"   url:"+matcherChild.group(1));
                    brand.setImgurl(matcherChild.group(1));
                    brand.setName(matcherChild.group(2));

                }
                //数据库插入brand  遇到异常跳出下一次继续执行
                try{
                    brandTempMapper.insertSelective(brand);
                }catch (Exception e){
                    logger.error("插入Brand  id="+brand.getId()+"  name="+brand.getName()+"出错:"+e.getMessage());
                    continue;
                }

                //System.out.println("品牌级别明细："+matcher.group(2));
                //获取厂商名称  group1 厂商 group2 车型
                patternChild = Pattern.compile("<div.*?class=\"h3-tit\">(.*?)</div>.*?<ul class=\"rank-list-ul\" 0>(.*?)</ul>",Pattern.DOTALL);
                matcherChild = patternChild.matcher(matcher.group(2));
                int manuId = 0;
                while (matcherChild.find()) {
                    CarManufacturerTemp manufacturer = new CarManufacturerTemp();
                    manufacturer.setBrandId(brand.getId());
                    manufacturer.setId(brand.getId()*1000+ manuId++);
                    manufacturer.setName(matcherChild.group(1));
                    manufacturer.setOrl(manuId*10+"");
                    // 插入manufacturer  遇到异常跳出下一次继续执行
                    try{
                        manufacturerTempMapper.insertSelective(manufacturer);
                    }catch (Exception e){
                        logger.error("插入Manufacturer  id="+manufacturer.getId()+"  name="+manufacturer.getName()+"出错:"+e.getMessage());
                        continue;
                    }
                    //System.out.println("   厂商名称：" + matcherChild.group(1));
                    // System.out.println("   车型明细：" + matcherChild.group(2));
                    //匹配车型 第三级别
                    //Pattern patternGrandchild  = Pattern.compile("<li.*?id=\"(.*?)\">.*?<h4><a.*?>(.*?)</a>.*?</h4>.*?指导价：<a.*?>(.*?)</a>.*?</li>",Pattern.DOTALL);
                    int orl = 10;
                    Pattern patternGrandchild  = Pattern.compile("<li.*?<h4.*?</li>",Pattern.DOTALL);
                    Matcher matcherGrandchild = patternGrandchild.matcher(matcherChild.group(2));
                    while (matcherGrandchild.find()) {
                        //System.out.println("车型:"+matcherGrandchild.group());
                        Pattern patternGrandchild2  = Pattern.compile("<li.*?id=\"s(.*?)\">.*?<h4.*?<a.*?href=\".*?\"(.*?)>(.*?)</a>.*?</h4>.*?指导价：(.*?)<div.*?</li>",Pattern.DOTALL);
                        Matcher matcherGrandchild2 = patternGrandchild2.matcher(matcherGrandchild.group());
                        while (matcherGrandchild2.find()) {
                            //System.out.println(matcherGrandchild2.group());
                            //System.out.println("     车型id：" + matcherGrandchild2.group(1) + "  名称：" + matcherGrandchild2.group(2) + "  指导价：" + matcherGrandchild2.group(3));
                            String price = matcherGrandchild2.group(4);
                            Pattern patternGrandchild3  = Pattern.compile("<a.*?>(.*?)</a",Pattern.DOTALL);
                            Matcher matcherGrandchild3 = patternGrandchild3.matcher(price);
                            while(matcherGrandchild3.find()){
                                price = matcherGrandchild3.group(1);
                            }
                            //System.out.println("price:"+price);
                            logger.info("     车型id：" + matcherGrandchild2.group(1) + "  名称：" + matcherGrandchild2.group(2) + "  指导价：" + price);
                            //System.out.println("group2:"+matcherGrandchild2.group(2));
                            CarSeriesTemp Series = new CarSeriesTemp();
                            Series.setId(Integer.parseInt(matcherGrandchild2.group(1)));
                            Series.setOrl(orl);
                            Series.setState((matcherGrandchild2.group(2)==null||matcherGrandchild2.group(2).equals(""))?"在售":"停售");
                            Series.setManuId(manufacturer.getId());
                            Series.setName(matcherGrandchild2.group(3));
                            Series.setPrice(price);
                            try{
                                seriesTempMapper.insertSelective(Series);
                            }catch (Exception e){
                                logger.error("插入Series  id="+Series.getId()+"  name="+Series.getName()+"出错:"+e.getMessage());
                                continue;
                            }
                        }
                        orl += 10;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CarSeriesTemp> getAllSeriesTemp(){
        CarSeriesTempExample all = new CarSeriesTempExample();
        List<CarSeriesTemp> series = seriesTempMapper.selectByExample(all);
        return  series;

    }



    public List<CarBrandTemp> getAllBrandTemp(){
        CarBrandTempExample all = new CarBrandTempExample();
        List<CarBrandTemp> brands = brandTempMapper.selectByExample(all);
        return  brands;
    }



    public List<CarSeries> getAllSeries(){
        CarSeriesExample all = new CarSeriesExample();
        List<CarSeries> series = carSeriesMapper.selectByExample(all);
        return  series;

    }


    public List<CarTypeTemp> getAllTypeTemp(){
        CarTypeTempExample all = new CarTypeTempExample();
        List<CarTypeTemp> types = typeTempMapper.selectByExample(all);
        return  types;
    }


    public void translateImgUrlToFile(String filepath, String filename , String imgurl){
        //int SeriesId = 18;
        //String filepath="D:\\imgs";
        //String imgurl = "http://car3.autoimg.cn/cardfs/product/g12/M0C/B2/CC/t_autohomecar__wKjBy1g9Va2AS7MbAAuBAsvFEvg627.jpg";
        try {
            byte[]  response = HttpUtil.sendGetForBytes(imgurl);
            File file = new File(filepath + File.separator + filename);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(response);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBrandByPrimaryKey(CarBrandTemp brand){
        brandTempMapper.updateByPrimaryKey(brand);
        return;
    }



}
