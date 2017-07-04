package com.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/20.
 */
public class HttpUtil {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     *
     * @return URL 所代表远程资源的响应结果
     */

    public static String sendGet(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param  charset
     *            设定接收的字符集
     * @return URL 所代表远程资源的响应结果
     */

    public static String sendGet(String url,String charset) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return new String(response.body().bytes(),charset);

    }

    public static byte[] sendGetForBytes(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().bytes();

    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 用来传送JsonObjdect参数
     * @param url
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public static String sendPost(String url,JSONObject jsonObject)throws Exception{
        String jsonStr = jsonObject.toString();
        return sendPostByJsonStr(url,jsonStr);
    }

    /**
     * 用来传送JsonStr参数
     * @param url
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static String sendPostByJsonStr(String url,String jsonStr)throws Exception{
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
       /* System.out.println(response.body());
        System.out.println(response.body().string());
        System.out.println(response.body().toString());
        System.out.println(response.headers());
        System.out.println(response);*/

        return response.body().string();
    }

    /**
     * 提供匹配选择的post方法
     * @param url
     * @param params
     * @param type
     * @return
     * @throws Exception
     */
    public static String sendPost(String url,String params,String type)throws Exception{
        if(type!=null && type.toLowerCase().indexOf("json")>=0){
            return sendPostByJsonStr(url,params);
        }else{
            return sendPost(url,params);
        }
    }


    private static final String userSig = PropertyUtils.getProperty("userSig");
    private static final String identifier = PropertyUtils.getProperty("identifier");
    private static final String sdkappid = PropertyUtils.getProperty("sdkAppId");
    private static final String httpUrl = "https://console.tim.qq.com/";

    /**
     * 该方法用来处理腾讯接口的Http请求  将JavaBean序列化为json格式后post
     * @param url
     * @param object
     * @return
     * @throws Exception
     */
    public static JSONObject TencentPost(String url, Object object) throws Exception{
        String params =  JSONObject.toJSONString(object);
        return TencentPost(url,params);

    }

    /**
     * 该方法用来处理腾讯接口的Http请求  params格式需为json格式
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static JSONObject TencentPost(String url, String params) throws Exception{
        String sendUrl = httpUrl+url+"?usersig="+userSig+"&apn=1&identifier="+identifier+"&sdkappid="+sdkappid+"&contenttype=json";
        System.out.println("send:"+params);
        String resultStr =  HttpUtil.sendPost(sendUrl,params,"json");
        System.out.println("receive:"+resultStr);
        return JSONObject.parseObject(resultStr);

    }












}
