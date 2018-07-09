package com.simple.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.simple.common.rest.ResultData;
import com.simple.common.rest.UniqueType;
import com.simple.domain.po.SysUserRole;
import com.simple.domain.po.UserInfo;
import com.simple.service.SysUserRoleService;
import com.simple.service.UserInfoService;
import com.simple.shiro.PasswordHelper;
import com.simple.shiro.UserSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author chenkx
 * @date 2018-01-05.
 */
@Api(description="用户相关接口")
@RestController
@RequestMapping("user")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    SysUserRoleService userRoleService;
   
    @ApiOperation(value = "添加用户接口", response = String.class)
    @PostMapping("add")
    public ResultData createUser(UserInfo userInfo){
        Assert.notNull(userInfo.getUsername(), "用户名不能为空");
        Assert.isTrue(checkUnique(userInfo.getUsername(), UniqueType.INSERT), "重复的用户名");
        Assert.notNull(userInfo.getPassword(), "密码不能为空");
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(userInfo);
        userInfoService.saveOrUpdate(userInfo);
        return new ResultData();
    }
    

    /**
     * 启用，停用用户
     * @param userId
     * @param status
     * @return
     */
    @ApiOperation(value = "禁用/启用用户接口", response = String.class)
    @PostMapping("updateStatus")
    @ApiImplicitParams({
  	  @ApiImplicitParam(name="userId",value="用户id",dataType="String", paramType = "query",required=true),
  	  @ApiImplicitParam(name="status",value="1:启用  0:禁用",dataType="int", paramType = "query",required=true)})
    public ResultData modifyStatus(String userId, 
    		Integer status) {
    	UserInfo userInfo = new UserInfo();
    	userInfo.setId(userId);
    	userInfo.setStatus(status);
        userInfoService.saveOrUpdate(userInfo);
        return new ResultData(userInfo);
    }
    
    
    @PostMapping("resetPassword")
    @ApiOperation(value = "重置密码/修改密码", response = String.class)
    @ApiImplicitParams({
    	 @ApiImplicitParam(name="userId",value="用户id",dataType="String", paramType = "query",required=true),
    	    @ApiImplicitParam(name="password",value="密码(重置密码不填)",dataType="String", paramType = "query",required=false)
    })
    public ResultData resetPassword(String userId,String password) {
    	if(StringUtils.isBlank(password)) {
    		password="123456";
    	}
    	UserInfo user = userInfoService.getById(userId);
    	user.setPassword(password);
    	PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(user);
    	
        UserInfo userInfo=new UserInfo();
        userInfo.setId(userId);
        userInfo.setPassword(user.getPassword());
        userInfoService.saveOrUpdate(userInfo);
    	return new ResultData();
    }
    
    
    
    
    @ApiOperation(value = "用户分页接口", response = String.class)
    @GetMapping("lists")
    public ResultData listAsPage(UserInfo userInfo, Integer pageNum, Integer pageSize){
        final PageInfo<UserInfo> page = userInfoService.listAsPage(userInfo, pageNum, pageSize);
        return new ResultData(page);
    }

    @ApiOperation(value = "用户详情接口", response = String.class)
    @GetMapping("detail")
    public ResultData detail(String id){
        final UserInfo user = userInfoService.getById(id);
        return new ResultData(user);
    }


    @ApiOperation(value = "修改用户接口", response = String.class)
    @PostMapping("update")
    public ResultData updateUser(UserInfo userInfo){
        Assert.notNull(userInfo.getUsername(), "用户名不能为空");
        Assert.isTrue(checkUnique(userInfo.getUsername(), UniqueType.UPDATE), "重复的用户名");
        Assert.notNull(userInfo.getPassword(), "密码不能为空");
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(userInfo);
        userInfoService.saveOrUpdate(userInfo);
        return new ResultData();
    }

    @ApiOperation(value = "删除用户接口", response = String.class)
    @GetMapping("/del")
    public ResultData deleteUser(String id) {
        userInfoService.deleteUser(id);
        return new ResultData();
    }


    @ApiOperation(value = "用户添加角色接口", response = String.class)
    @PostMapping("setRoles")
    public ResultData setRoles(String userId,String roleIds) {
        String[] roIds = roleIds.split(",");
        List<SysUserRole> u2rs = new ArrayList<>(roIds.length);
        for (String roleId : roIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            userRole.setUid(userId);
            u2rs.add(userRole);
        }
        userRoleService.batchInsert(u2rs);
        return new ResultData();
    }

    private boolean checkUnique(String username, UniqueType checkType) {
        if (checkType == UniqueType.INSERT) {
            if (userInfoService.cntByUserName(username) > 0) {
                return false;
            }
        }
        if (checkType == UniqueType.UPDATE) {
            if (userInfoService.cntByUserName(username) > 1) {
                return false;
            }
        }
        return true;
    }
    
    
    @GetMapping("hasButtonPermission")
    public ResultData hasButtonPermission(String permissions){
    	Map<String,Boolean> map=new HashMap<>();
    	for(String name:permissions.split(",")){
    		 String userId = (String) 
    				 SecurityUtils.getSubject().getSession().getAttribute(UserSession.userId);
    		boolean has =userInfoService.hasButtonPermission(userId, name);
    		map.put(name, has);
    	}
//    	StringBuffer buffer = new StringBuffer();
//    	for(Entry<String, Boolean> entrySet :map.entrySet()){
//    		buffer.append("var "+entrySet.getKey());
//    		buffer.append("="+entrySet.getValue());
//    		buffer.append(";");
//    	}
    	//return new ResultData(buffer.toString());
    	return new ResultData(map);
    }
    
    @GetMapping("getUser")
    public ResultData getUserInfo(){
    	UserInfo info =(UserInfo) SecurityUtils.getSubject().getSession().getAttribute(UserSession.userInfo);
    	ResultData data = new ResultData();
    	data.data=info;
    	return data;
    }
}
