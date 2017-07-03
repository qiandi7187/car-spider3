package com.fxyz.chebao.controller.v1.file;

import com.fxyz.chebao.pojo.User;
import com.fxyz.chebao.pojo.common.ResponseData;
import com.fxyz.chebao.service.IUserService;
import com.util.PropertyUtils;
import com.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by rjhuang on 2017/5/9.
 * 文件接口
 */
@Controller
@RequestMapping("/v1/file")
public class FileController {
    @Resource
    IUserService userService;
    @RequestMapping("head")
    public @ResponseBody  ResponseData uploadHead(HttpServletRequest request, String uid)
    {
        try {
            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //检查form中是否有enctype="multipart/form-data"
            User user = userService.selectByPrimaryKey(Integer.parseInt(uid));
            if(user==null) return ResponseData.customerError().putDataValue("msg","用户不存在");

            if(multipartResolver.isMultipart(request))
            {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iter=multiRequest.getFileNames();

                while(iter.hasNext())
                {
                    //一次遍历所有文件
                    MultipartFile file=multiRequest.getFile(iter.next().toString());
                    if(file!=null)
                    {
                        PropertyUtils.load("config/property/file.properties");
                        String path=PropertyUtils.getProperty("head")+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ StringUtil.getRandom(4)+"_.jpg";
                        //上传
                        file.transferTo(new File(PropertyUtils.getProperty("rootPath")+path));
                        user.setFaceUrl(path);
                        userService.updateByPrimaryKeySelective(user);
                    }

                }

            }
            return ResponseData.ok().putDataValue("faceUrl",PropertyUtils.getProperty("domain")+user.getFaceUrl());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError();
        }

    }


    @RequestMapping("cluster")
    public @ResponseBody  ResponseData uploadCluster(HttpServletRequest request)
    {
        try {
            String path = uploadFile(request,"cluster","jpg");
            return ResponseData.ok().putDataValue("faceUrl",path);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError();
        }

    }
    public String uploadFile(HttpServletRequest request,String location,String type) throws Exception{
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        String path = "";
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();

            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    PropertyUtils.load("config/property/file.properties");
                    path =PropertyUtils.getProperty(location)+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ StringUtil.getRandom(4)+"_"+type;
                    //上传
                    file.transferTo(new File(PropertyUtils.getProperty("rootPath")+path));
                }

            }

        }
        return PropertyUtils.getProperty("domain")+path;
    }
}
