package com.fxyz.chebao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxyz.chebao.mapper.*;
import com.fxyz.chebao.pojo.carSpider.*;
import com.fxyz.chebao.service.ICarSpiderService;
import com.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CarSpiderServiceImpl implements ICarSpiderService {

    @Autowired
    CarBrandTempMapper brandTempMapper;
    @Autowired
    CarManufacturerTempMapper manufacturerTempMapper;
    @Autowired
    CarModelTempMapper modelTempMapper;
    @Autowired
    CarTypeTempMapper typeTempMapper;


    @Override
    public void getCarTypeStopModelById(int modelId){
        //int modelInterId = 19;
        try {
            Document doc = Jsoup.connect("http://www.autohome.com.cn/"+ modelId).get();
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
                    System.out.println("抓取id出错");
                }
                type.setModelId(modelId);
                type.setName(tr.select(".name_d a").text());
                type.setSecondPrice(tr.select(".price_d span a").text());
                System.out.println("type  id:"+type.getId()+"   name:"+type.getName()+"  guideprice: "+type.getGuidePrice()+"  dealerPrice:"+type.getDealerPrice()+
                        "   SecondPrice:"+type.getSecondPrice()+"   setTransmission:"+type.getTransmission());
                typeTempMapper.insertSelective(type);
                orl+=10;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
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




    @Override
    public void getCarTypeStopSaleById(int modelId){
        //int modelInterId = 19;
        try {
            Document doc = Jsoup.connect("http://www.autohome.com.cn/"+ modelId).get();
            //配置正常在售车系的html模板
            Elements as = doc.select("#drop2 ul li a");
            System.out.println(as.size());
            for (Element a : as) {
                String yid = a.attr("data");
                //System.out.println(yid);
                String response = HttpUtil.sendGet("http://www.autohome.com.cn/ashx/series_allspec.ashx?s=" + modelId + "&y=" + yid + "&l=3");
                JSONObject resJson = JSONObject.parseObject(response);
                JSONArray specArr = resJson.getJSONArray("Spec");

                for (int i = 0; i < specArr.size(); i++) {
                    CarTypeTemp type = new CarTypeTemp();
                    JSONObject typeJson = specArr.getJSONObject(i);
                    System.out.println(typeJson);
                    type.setOrl(i*10+10);
                    try {
                        type.setId(typeJson.getIntValue("Id"));
                    }catch (Exception e){
                        System.out.println("获取id失败");
                    }
                    type.setModelId(modelId);
                    type.setDrivingMode(typeJson.getString("DrivingModeName"));
                    type.setTransmission(typeJson.getString("Transmission"));
                    type.setGuidePrice(typeJson.getString("Price"));
                    type.setSecondPrice(typeJson.getString("Price2Sc"));
                    type.setName(typeJson.getString("Name"));
                    type.setGroupName(typeJson.getString("GroupName"));
                    type.setTax(typeJson.getBoolean("ShowTaxRelief")==true?"减税":null);
                    type.setState("停售");
                    System.out.println("type  id:"+type.getId()+"   name:"+type.getName()+"  guideprice: "+type.getGuidePrice()+"  dealerPrice:"+type.getDealerPrice()+
                            "   DrivingMode:"+type.getDrivingMode()+"   setTransmission:"+type.getTransmission());
                    typeTempMapper.insertSelective(type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void getCarTypeOnSaleById( int modelId ){
        //int modelInterId = 19;
        try{
            Document doc = Jsoup.connect("http://www.autohome.com.cn/" + modelId).get();
            //生成第四层信息
            Elements lis = doc.select("#speclist .current ul li");
            int orl = 10;
            for (Element li : lis) {
                String liStr = li.html();
                // System.out.println("--------------------------------------------------");
                // System.out.println(liStr);
                CarTypeTemp type = new CarTypeTemp();
                type.setModelId(modelId);
                type.setOrl(orl);
                try{
                    String href = li.select(".interval01-list-cars-infor p:eq(0) a").attr("href");
                    Pattern p = Pattern.compile("/spec/(.*?)/#pvareaid.*?");
                    Matcher m = p.matcher(href);
                    if(m.matches()) {
                        type.setId(Integer.parseInt( m.group(1)));
                    }
                }catch (Exception e){
                    System.out.println("抓取id出错");
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

                System.out.println("type  id:"+type.getId()+"   name:"+type.getName()+"  guideprice: "+type.getGuidePrice()+"  dealerPrice:"+type.getDealerPrice()+
                        "   DrivingMode:"+type.getDrivingMode()+"   setTransmission:"+type.getTransmission());
                typeTempMapper.insertSelective(type);
                orl += 10;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
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
                    System.out.println("品牌id：" + matcherChild.group(1)+"    olr:"+matcherChild.group(2));
                    try {
                        brand.setId(Integer.parseInt(matcherChild.group(1)));
                    }catch (Exception e){
                        System.out.println("获取ID失败");
                    }

                    brand.setOrl(matcherChild.group(2));
                    brand.setLetter(lettter);
                }
                //System.out.println("品牌："+matcher.group(1));
                //获取品牌名称
                patternChild = Pattern.compile("<dt.*?<img.*?src=\"(.*?)\">.*?<div><a.*?>(.*?)</a>.*?</dt>",Pattern.DOTALL);
                matcherChild = patternChild.matcher(matcher.group(1));
                while (matcherChild.find()) {
                    System.out.println("品牌名称：" + matcherChild.group(2)+"   url:"+matcherChild.group(1));
                    brand.setImgurl(matcherChild.group(1));
                    brand.setName(matcherChild.group(2));

                }
                //数据库插入brand  遇到异常跳出下一次继续执行
                try{
                    brandTempMapper.insertSelective(brand);
                }catch (Exception e){
                    System.out.println("插入Brand  id="+brand.getId()+"  name="+brand.getName()+"出错:"+e.getMessage());
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
                        System.out.println("插入Manufacturer  id="+manufacturer.getId()+"  name="+manufacturer.getName()+"出错:"+e.getMessage());
                        continue;
                    }
                    System.out.println("   厂商名称：" + matcherChild.group(1));
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
                            System.out.println("     车型id：" + matcherGrandchild2.group(1) + "  名称：" + matcherGrandchild2.group(2) + "  指导价：" + price);
                            System.out.println("group2:"+matcherGrandchild2.group(2));
                            CarModelTemp model = new CarModelTemp();
                            model.setId(Integer.parseInt(matcherGrandchild2.group(1)));
                            model.setOrl(orl);
                            model.setState((matcherGrandchild2.group(2)==null||matcherGrandchild2.group(2).equals(""))?"在售":"停售");
                            model.setManuId(manufacturer.getId());
                            model.setName(matcherGrandchild2.group(3));
                            model.setPrice(price);
                            try{
                                modelTempMapper.insertSelective(model);
                            }catch (Exception e){
                                System.out.println("插入Model  id="+model.getId()+"  name="+model.getName()+"出错:"+e.getMessage());
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

    @Override
    public List<CarModelTemp> getAllModels(){
        CarModelTempExample all = new CarModelTempExample();
        List<CarModelTemp> models = modelTempMapper.selectByExample(all);
        return  models;

    }



}