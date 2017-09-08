package com.fxyz.chebao.service;

import com.fxyz.chebao.mapper.*;
import com.fxyz.chebao.pojo.petrol.PetrolPrice;
import com.fxyz.chebao.pojo.petrol.PetrolPriceExample;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时自动爬取汽油价格  后期可考虑多种途径
 */
@Service
public class PetrolPriceSpiderService {
    private static Logger logger = Logger.getLogger(PetrolPriceSpiderService.class);

    @Autowired
    PetrolPriceMapper petrolPriceMapper;



    public void getNewPetrolPrice(){
        try {
            logger.info("开始抓取汽油价格数据！");
            Document doc = Jsoup.connect("http://ny.gold600.com/").get();
            //System.out.println(doc.html());
            Elements tr = doc.select(".oilTable tbody tr");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for(Element ele:tr) {
                if (ele.html() == null || ele.html().equals("")) {
                    continue;
                }
                String city = ele.select("td:first-child a").text();
                String price90 = ele.select("td:eq(1)").text();
                String price93 = ele.select("td:eq(2)").text();
                String price97 = ele.select("td:eq(3)").text();
                String price0 = ele.select("td:eq(4)").text();
                Date date = null;
                try {
                    date = format.parse(ele.select("td:eq(5)").text());
                } catch (Exception e) {
                    logger.error("未获取到当前更新时间：" + city);
                    continue;
                }
                PetrolPrice price = new PetrolPrice();
                price.setCity(city);
                price.setPrice0(price90);
                price.setPrice90(price93);
                price.setPrice93(price97);
                price.setPrice97(price0);
                price.setUpdateTime(date);
                savePrice(price);
            }
        } catch (Exception e) {
            logger.error("获取最新汽油价格出错：" + e.getMessage());
        }

    }

    /**
     * 存入前需判决是否是最新数据，是则更新，否则不更新
     * @param price
     */
    private void savePrice(PetrolPrice price){
        PetrolPriceExample example = new PetrolPriceExample();
        example.or().andCityEqualTo(price.getCity()).andUpdateTimeGreaterThanOrEqualTo(price.getUpdateTime());
        if(petrolPriceMapper.selectByExample(example).size()>0){
            return;
        }
        petrolPriceMapper.insertSelective(price);
    }




}
