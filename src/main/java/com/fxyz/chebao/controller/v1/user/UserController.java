package com.fxyz.chebao.controller.v1.user;

import com.fxyz.chebao.pojo.User;
import com.fxyz.chebao.pojo.UserLocation;
import com.fxyz.chebao.pojo.common.ResponseData;
import com.fxyz.chebao.service.IUserService;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/20.
 */
@Controller
@RequestMapping("/v1/user")
public class UserController {
    @Resource
    IUserService userService;

    /**
     * 注册接口
     * @param user
     * @return
     */
    @RequestMapping("register")
    public @ResponseBody ResponseData regist(@RequestBody User user){
        try {
            int result = userService.insertSelective(user);
            if(result==0) return ResponseData.customerError(10001,"用户已存在");

            return ResponseData.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError().putDataValue("Exception","注册异常");
        }

    }

    /**
     * 登录接口
     * @param user
     * @return
     */
    @RequestMapping(value="login")
    public @ResponseBody ResponseData login(@RequestBody User user){
        try {
            int result = userService.login(user);
            if(result==1) {
                ResponseData responseData = ResponseData.ok();

                responseData.putDataValue("token",user.getToken());
                responseData.putDataValue("uid",user.getUid());
                responseData.putDataValue("sig",user.getSig());
                return responseData;
            }
            if(result==0) return ResponseData.customerError(1001,"密码错误");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError(1002,"登录异常");
        }
        return ResponseData.customerError(1003,"用户不存在");
    }

    /**
     * 用户信息修改
     * @param user
     * @return
     */
    @RequestMapping("profile/portrait_set")
    public @ResponseBody ResponseData portraitSet(@RequestBody User user){
        try {
            int result = userService.updateByPrimaryKeySelective(user);
            if(result==0) return ResponseData.customerError(10000,"修改信息需要登录");
            if(result>0) return ResponseData.ok().putDataValue("msg","用户信息修改成功");
            else return ResponseData.customerError(10002,"用户信息修改错误");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError(10001,"用户信息修改异常");
        }

    }

    /**
     * 根据条件查询用户
     * @param user
     * @return
     */
    @RequestMapping("condition/list")
    public @ResponseBody ResponseData findUser(@RequestBody User user){
        try {
            List<User> users = userService.findUserByCondition(user);
            return ResponseData.ok().putDataValue("list",users);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError();
        }

    }

    /**
     * 位置上传
     * @param userLocation
     * @return
     */
    @RequestMapping("/lbs/add")
    public @ResponseBody ResponseData saveLocation(@RequestBody UserLocation userLocation){
        try {
            userService.saveLocation(userLocation);
            return ResponseData.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError(10001,"位置上传异常");
        }
    }

    /**
     * 根据位置查询附近的人
     * @param latitude
     * @param longitude
     * @return
     */
    @RequestMapping(value = "/lbs/list",method= RequestMethod.GET )
    public @ResponseBody ResponseData findByRange(double latitude,double longitude){
        try {
            GeoResults<UserLocation> geoResults = userService.findByRange(latitude,longitude);
            List<GeoResult<UserLocation>> userLocations = geoResults.getContent();
            List<Map<String,Object>> resultList = new ArrayList<Map<String, Object>>();
            for(int i = 0;i<userLocations.size();i++){

                UserLocation  userLocation = userLocations.get(i).getContent();
                Map<String,Object> result = new HashMap();
                result.put("distance",userLocations.get(i).getDistance());
                result.put("startTime",userLocation.getStartTime());
                result.put("location",userLocation.getLocation());

                User user = userService.selectByPrimaryKey(Integer.parseInt(userLocation.getUid()));
                result.put("user",user);
                resultList.add(result);
            }
            return ResponseData.ok().putDataValue("results",resultList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError(10001,"附近查询异常");
        }
    }
}
