package com.fxyz.chebao.nickname;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxyz.chebao.mapper.CarBrandTempMapper;
import com.fxyz.chebao.mapper.NicknameSpiderMapper;
import com.fxyz.chebao.pojo.car.CarSeriesTemp;
import com.fxyz.chebao.pojo.car.NicknameSpider;
import com.fxyz.chebao.pojo.car.NicknameSpiderExample;
import com.util.HttpUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 抓取昵称给一元行使用
 * 业务简单，直接调用mapper
 * 效率低，改成异步模式
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring/applicationContext-*.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class nicknameSpiderTest {
    @Autowired
    NicknameSpiderMapper nicknameSpiderMapper;



    /**
     * 异步模式
     */
    @Test
    public void getNicknameUnSysn(){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        int count = 0;
        int sum = 380013;
        //解决内存不释放问题  按批获取
        int batchSize = 160;
        for(int i=2042;i<sum/batchSize+1;i++){
            List<Future> list = new ArrayList<Future>();
            for(int j=0;j<batchSize && i*batchSize+j<sum;j++){
                Integer id = i*batchSize+j;
                Future f = cachedThreadPool.submit( ()-> {
                    Map map=new HashMap();
                    String result =null;
                    try{
                        result = sendRquest(id);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    map.put("result",result);
                    return map;
                });
                list.add(f);
            }
            System.out.println("#############     总记："+(sum/batchSize+1 )+ "   第"+(i+1)+"次    ################");
            for(Future f : list) {
                try {
                    Map map=(Map) f.get(1000, TimeUnit.MILLISECONDS);
                    String  result = (String)map.get("result");
                    decodeNickname(result);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        cachedThreadPool.shutdown();
    }

    public String sendRquest(int id) throws Exception {
        String result = HttpUtil.sendGet("http://m2.uqixun.com/mobile/shopajax/getBuyRecordList/"+id+"/0");
        return result;
    }

    public void decodeNickname(String result) throws Exception {
        //System.out.println(result);
        JSONObject resultJson = JSONObject.parseObject(result);
        JSONArray jsonArr = resultJson.getJSONArray("listItems");
        for(Object o:jsonArr){
            JSONObject jb = (JSONObject)o;
            saveNickname(jb.getString("uname"));
        }
    }

    /**
     * 同步模式
     * @throws Exception
     */
    @Test
    public void getNickname() throws Exception {
        for(int i=380013;i>0;i++){
            getNickname(i);
        }
    }


    /**
     * 同步模式调用
     * @param id
     */
    private void getNickname(int id)  {
        try {
            String result = HttpUtil.sendGet("http://m2.uqixun.com/mobile/shopajax/getBuyRecordList/"+id+"/0");
            JSONObject resultJson = JSONObject.parseObject(result);
            JSONArray jsonArr = resultJson.getJSONArray("listItems");
            for(Object o:jsonArr){
                JSONObject jb = (JSONObject)o;
                saveNickname(jb.getString("uname"));
            }
            System.out.println(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveNickname(String nickname){
        if(nickname.contains("****")){
            return;
        }
        NicknameSpiderExample example = new NicknameSpiderExample();
        example.or().andNameEqualTo(nickname);
        if(nicknameSpiderMapper.selectByExample(example).size()<1){
            NicknameSpider name = new NicknameSpider();
            name.setName(nickname);
            System.out.println("存入："+nickname);
            nicknameSpiderMapper.insertSelective(name);
        }
    }




}
