package com.util;

import java.util.Random;

/**
 * Created by Administrator on 2017/5/11.
 */
public class StringUtil {
    /**
     * 根据传入的长度获取随机数字符串
     * @param length 需要的随机数长度
     * @return
     */
    public static String getRandom(int length){
        StringBuffer result = new StringBuffer("");
        Random random =  new Random();
        for(int i =0;i<length;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
