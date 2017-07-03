package com.fxyz.chebao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fxyz.chebao.mapper.GroupMapper;
import com.fxyz.chebao.pojo.Group;
import com.fxyz.chebao.service.IGroupService;
import com.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class GroupServiceImpl implements IGroupService {

    @Autowired
    GroupMapper groupMapper;

    /**
     * @param group
     * @return
     * @throws Exception
     */
    @Override
    public int addGroup(Group group) throws Exception {
        //校验
        if(group.getName() ==null || "".equals(group.getName())){
            throw new Exception("聊天室名称为空");
        }
        //默认AVChatRoom（互动直播聊天室）
        if(group.getType()==null || "".equals(group.getType())){
            group.setType("AVChatRoom");
        }
        //添加必要信息存入本地数据库
        String uuid = UUID.randomUUID().toString();
        group.setGroupid(uuid);
        group.setValid(0);//0 有效  1 无效
        group.setCreateTime(new Date());
        int result  = groupMapper.insertSelective(group);
        JSONObject rstJson  = HttpUtil.TencentPost("v4/group_open_http_svc/create_group",group);
        //添加失败返回，本地不保存相关数据
        if (rstJson.getIntValue("ErrorCode")!=0){
            throw new RuntimeException("腾讯接口调用失败："+rstJson.getString("ErrorInfo"));
        }
        return result;
    }

    @Override
    public int deleteByPrimaryKey(Group group) throws Exception {
        //删除本地数据库相关数据  实际调用的是update
        group.setValid(1);//0 有效  1 无效
        int result  = groupMapper.updateByPrimaryKeySelective(group);
        JSONObject rstJson  = HttpUtil.TencentPost("v4/group_open_http_svc/destroy_group",group);
        //失败返回，本地不保存相关数据
        if (rstJson.getIntValue("ErrorCode")!=0){
            throw new RuntimeException("腾讯接口调用失败："+rstJson.getString("ErrorInfo"));
        }

        return result;
    }

    /**
     * 根据类型查找群组
     * @param type 群组类型
     * @return 返回群组集合
     */
    @Override
    public List<Group> findGroupsByType(String type) {
        return groupMapper.findGroupsByType(type);
    }

}
