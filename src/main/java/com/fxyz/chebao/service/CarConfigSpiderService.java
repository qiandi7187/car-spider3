package com.fxyz.chebao.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxyz.chebao.mapper.CarConfigItemTempMapper;
import com.fxyz.chebao.mapper.CarConfigTempMapper;
import com.fxyz.chebao.mapper.CarSpiderErrMapper;
import com.fxyz.chebao.mapper.CarTypeTempMapper;
import com.fxyz.chebao.pojo.carSpider.*;
import com.util.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CarConfigSpiderService {
    private static Logger logger = Logger.getLogger(CarConfigSpiderService.class);

    @Autowired
    CarTypeTempMapper typeTempMapper;
    @Autowired
    CarConfigItemTempMapper configItemTempMapper;
    @Autowired
    CarConfigTempMapper configTempMapper;
    @Autowired
    CarSpiderErrMapper spiderErrMapper;


    //设置批量提交的缓存行数
    private static final int insertBatchSize = 10000;
    //存储批量提交的对象
    private static List<CarConfigTemp> configTempBatchlist = new ArrayList<CarConfigTemp>();

    /**
     * @param speclist  格式 31150,2249,1546,30427 一次最多6个
     * @return
     */
    public String sendParamItemById(String speclist) {
        try {
            return HttpUtil.sendGet("https://carif.api.autohome.com.cn/Car/Spec_ParamListBySpecList.ashx?_callback=paramCallback&speclist="+speclist);
        } catch (Exception e) {
            e.printStackTrace();
            CarSpiderErr err = new CarSpiderErr();
            err.setValue0(speclist);
            err.setDesp(e.getMessage());
            err.setMethodName("sendParamItemById");
            spiderErrMapper.insert(err);
        }
        return null;
    }

    /**
     * @param speclist  格式 31150,2249,1546,30427   一次最多6个
     * @return
     */
    public String sendConfigItemById(String speclist) {
        try {
            return HttpUtil.sendGet("http://carif.api.autohome.com.cn/Car/Config_ListBySpecIdList.ashx?_callback=configCallback&speclist="+speclist);
        } catch (Exception e) {
            e.printStackTrace();
            CarSpiderErr err = new CarSpiderErr();
            err.setValue0(speclist);
            err.setDesp(e.getMessage());
            err.setMethodName("sendConfigItemById");
            spiderErrMapper.insert(err);
        }
        return null;
    }

    public void decodeParamItemById(String  result,String speclist ){
        decodeParamAndConfigItemById(result,"paramtypeitems","paramitems",speclist);
    }
    public void decodeConfigItemById(String  result,String speclist ){
        decodeParamAndConfigItemById(result,"configtypeitems","configitems",speclist);
    }

    /**
     * Param  Config  两种请求格式仅有 typeitems  items 不同   考虑合并
     * @param result
     * @param typeitemsDecodeName
     * @param itemsDecodeName
     */
    private void decodeParamAndConfigItemById(String  result,String typeitemsDecodeName,String itemsDecodeName,String speclist) {
        try {
            JSONObject resJson = JSONObject.parseObject(result.substring(result.indexOf("(")+1,result.lastIndexOf(")")));
            System.out.println(resJson);
            JSONArray paramtypeitemsJsonArr = resJson.getJSONObject("result").getJSONArray(typeitemsDecodeName);
            for(Object paramtypeitem : paramtypeitemsJsonArr){
                JSONObject paramtypeitemJson = (JSONObject) paramtypeitem;
                String paramtypeItemTypeName = paramtypeitemJson.getString("name");
                //System.out.println(paramtypeItemTypeName);
                //判定第一层itemType数据是否存在，不存在则插入数据库
                saveItemIfNotExists(paramtypeItemTypeName, 1,null);
                JSONArray paramitemsJsonArr = paramtypeitemJson.getJSONArray(itemsDecodeName);
                for(Object paramitem : paramitemsJsonArr){
                    JSONObject paramitemJson = (JSONObject) paramitem;
                    String paramitemName = paramitemJson.getString("name");
                    //System.out.println(paramitemName);
                    //判定第二层item数据是否存在，不存在则插入数据库
                    saveItemIfNotExists(paramitemName, 2,paramtypeItemTypeName);
                    //获取第三层 config数据，value不为空则直接插入数据库
                    JSONArray valueitemsJsonArr = paramitemJson.getJSONArray("valueitems");
                    for(Object valueitem :valueitemsJsonArr){
                        JSONObject valueitemJson = (JSONObject) valueitem;
                        //System.out.println(valueitemJson);
                        String value = valueitemJson.getString("value");
                        if(value != null  && !value.trim().equals("-")){
                            CarConfigTemp configTemp = new CarConfigTemp();
                            configTemp.setItemTypeName(paramtypeItemTypeName);
                            configTemp.setItemName(paramitemName);
                            configTemp.setValue(valueitemJson.getString("value"));
                            configTemp.setTypeId(valueitemJson.getInteger("specid"));
                            insertConfigBatch(configTemp);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CarSpiderErr err = new CarSpiderErr();
            err.setValue0(speclist);
            err.setValue1(typeitemsDecodeName);
            err.setDesp(e.getMessage());
            err.setMethodName("decodeParamAndConfigItemById");
            spiderErrMapper.insert(err);
        }
    }

    /**
     * CarConfigTemp行数多，单行数据量少，为了增加数据库存储效率，采用批量存储
     * @param configTemp
     * @return
     */
     void insertConfigBatch(CarConfigTemp configTemp){
        configTempBatchlist.add(configTemp);
        if(configTempBatchlist.size()>=insertBatchSize){
            configTempMapper.insertBatch(configTempBatchlist);
            configTempBatchlist = new  ArrayList<CarConfigTemp>();
        }

    }

    /**
     * 强制批量提交
     * @param
     * @return
     */
    public void insertConfigBatchFlush(){
        System.out.println("flush!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if(configTempBatchlist.size()>0){
            configTempMapper.insertBatch(configTempBatchlist);
            configTempBatchlist = new  ArrayList<CarConfigTemp>();
        }
    }


    /**
     * 查看属性是否存在，如果不存在就保存
     * 在确认item数据完备后跳过该步骤
     * @param name
     * @param level
     * @param pName
     */
    private void saveItemIfNotExists(String name,Integer level,String pName){
        CarConfigItemTempExample itemTempExample = new CarConfigItemTempExample();
        itemTempExample.or().andNameEqualTo(name).andLevelEqualTo(level);
        List<CarConfigItemTemp> list = configItemTempMapper.selectByExample(itemTempExample);
        if(list.size()==0){
            CarConfigItemTemp configItemTemp = new CarConfigItemTemp();
            configItemTemp.setLevel(level);
            configItemTemp.setName(name);
            configItemTemp.setpName(pName);
            configItemTempMapper.insert(configItemTemp);
        }
    }

    /**
     * 获取错误的Speclist
     * @return
     */
    public List<CarSpiderErr> getErrSpeclist(){
        Set<String> ErrSpeclist = new HashSet<String>();
        CarSpiderErrExample errExample = new CarSpiderErrExample();
        errExample.or().andMethodNameEqualTo("decodeParamAndConfigItemById");
        List<CarSpiderErr> list = spiderErrMapper.selectByExample(errExample);
        return list;
    }

    /**
     * 为item进行排序
     */
    public void sortConfigItem(){
        CarConfigItemTempExample itemTempExample = new CarConfigItemTempExample();
        itemTempExample.setOrderByClause("id");
        itemTempExample.or().andLevelEqualTo(1);
        List<CarConfigItemTemp> list = configItemTempMapper.selectByExample(itemTempExample);
        for(int i=0;i<list.size();i++){
            CarConfigItemTemp itemType = list.get(i);
            itemType.setSort(i*10+10);
            configItemTempMapper.updateByPrimaryKeySelective(itemType);
            //第二级排序
            CarConfigItemTempExample itemExample = new CarConfigItemTempExample();
            itemExample.setOrderByClause("id");
            itemExample.or().andLevelEqualTo(2).andPNameEqualTo(itemType.getName());
            List<CarConfigItemTemp> items = configItemTempMapper.selectByExample(itemExample);
            for(int j=0;j<items.size();j++){
                CarConfigItemTemp item = items.get(j);
                item.setSort(j*10+10);
                configItemTempMapper.updateByPrimaryKeySelective(item);
            }
        }
    }


}
