package com.fxyz.chebao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fxyz.chebao.dao.mongodb.IUserLocationDao;
import com.fxyz.chebao.mapper.UserMapper;
import com.fxyz.chebao.pojo.User;
import com.fxyz.chebao.pojo.UserLocation;
import com.fxyz.chebao.service.IUserService;
import com.tls.sigcheck.TLSUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */
@Service("userService")
public class UserServiceImpl implements IUserService{
    @Resource
    UserMapper userMapper;
    @Resource
    RedisTemplate redisTemplate;
    @Autowired
    IUserLocationDao userLocationDao;

    @Override
    public List<User> findUserByCondition(User user) {
        return userMapper.findUserByCondition(user);
    }

    @Override
    public User selectByPrimaryKey(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public int login(User user) {
        User existUser = userMapper.selectByUsername(user.getUsername());
        if(existUser==null) return -1;
        if(!existUser.getPassword().equals(user.getPassword())) return 0;
        try {
            user.setUid(existUser.getUid());
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create().withClaim("userID",existUser.getUid())
                    .sign(algorithm);
            redisTemplate.opsForValue().set("token_"+user.getUid(),token);
            user.setToken(token);
            String sig = (String) redisTemplate.opsForValue().get("chebao_sig"+user.getUid());
            if(sig==""||sig==null){
                sig = TLSUtil.getSig(user.getUid()+"");
            }
            user.setSig(sig);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public int updateByUsernameSelective(User user) {
        return userMapper.updateByUsernameSelective(user);
    }

    /**
     * 用户信息修改
     * @param user
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(User user) {
        if(!user.getToken().equals(redisTemplate.opsForValue().get("token_")+user.getUid())) return 0;
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 保存用户
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int insertSelective(User user) throws Exception{
        String username = user.getUsername();
        //if(!CheckUtil.checkCellphone(username)) throw new Exception("手机号错误");
        User currentUser =userMapper.selectByUsername(username);
        if(currentUser!=null) return 0;
        userMapper.insertSelective(user);
        currentUser=userMapper.selectByUsername(username);
        String sig = "";
        sig = TLSUtil.getSig(currentUser.getUid()+"");
        //导入腾讯账号
        //admin sig
        String adminSig = (String) redisTemplate.opsForValue().get("adminSig");
        if(adminSig==null||adminSig.equals("")) adminSig = TLSUtil.getSig("wnchebao");
        redisTemplate.opsForValue().set("adminSig",adminSig);
        String url = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?usersig="+adminSig+"&apn=1&identifier=wnchebao&sdkappid=1400029072&contenttype=json";
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Identifier",currentUser.getUid()+"");
        jsonObject.put("Nick",user.getNickname());
        jsonObject.put("FaceUrl",user.getFaceUrl());
        String json = jsonObject.toString();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        redisTemplate.opsForValue().set("chebao_sig"+currentUser.getUid(),sig);
        user.setSig(sig);

        return 1;
    }

    /**
     * 根据坐标查询附近的人
     * @param latitude 坐标
     * @param longitude
     * @return
     */
    @Override
    public GeoResults<UserLocation> findByRange(double latitude, double longitude) {
        return userLocationDao.findByRange(latitude,longitude);
    }

    /**
     * 保存坐标点
     * @param userLocation
     * @return
     */
    public int saveLocation(UserLocation userLocation){
        userLocationDao.saveUser(userLocation);

        return 1;
    }
}
