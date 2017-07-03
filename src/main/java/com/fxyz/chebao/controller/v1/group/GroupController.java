package com.fxyz.chebao.controller.v1.group;

import com.fxyz.chebao.pojo.Group;
import com.fxyz.chebao.pojo.common.ResponseData;
import com.fxyz.chebao.service.IGroupService;
import com.tls.sigcheck.TLSUtil;
import okhttp3.internal.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */
@Controller
@RequestMapping("/v1/group")
public class GroupController {
    @Resource
    IGroupService groupService;

    /**
     *
     * @param Group
     * @return
     */
    @RequestMapping(value = "add" ,method = RequestMethod.POST)
    public @ResponseBody ResponseData createGroup(@RequestBody Group Group){
        try {
            int result  = groupService.addGroup(Group);
            if(result==1){
                return ResponseData.ok();
            }else{
                return ResponseData.customerError();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError().putDataValue("Exception",e.getMessage());
        }
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST )
    public @ResponseBody ResponseData deleteGroup(@RequestBody Group Group){
        try {
            int result  = groupService.deleteByPrimaryKey(Group);
            if(result==1){
                return ResponseData.ok();
            }else{
                return ResponseData.customerError();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError().putDataValue("Exception",e.getMessage());
        }
    }


    @RequestMapping(value = "list",method = RequestMethod.GET)
    public @ResponseBody ResponseData findGroupsByType(String type){
        try {
            List<Group> groups = groupService.findGroupsByType(type);
            return ResponseData.ok().putDataValue("groups",groups);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.customerError(10001,"查询群组异常");
        }
    }

    @RequestMapping(value = "sign",method = RequestMethod.GET)
    public @ResponseBody ResponseData getSig(String ip){
        try {
            String sig = TLSUtil.getSig(ip);
            return ResponseData.ok().putDataValue("sig",sig);
        }catch (Exception e){
            return ResponseData.customerError(10001,"获取sig异常");
        }
    }
}
