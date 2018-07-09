package com.simple.service.impl;

import java.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;
import com.simple.domain.po.UserInfo;
import com.simple.mapper.UserInfoMapper;
import com.simple.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.simple.domain.po.SysUserRole;
import com.simple.mapper.SysUserRoleMapper;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
    UserInfoMapper userInfoMapper;
	@Autowired
	SysUserRoleMapper userRoleMapper;


    @Override
    public PageInfo<UserInfo> listAsPage(UserInfo record, Integer pageIndex, Integer pageSize) {
        return PageHelper.startPage(pageIndex, pageSize).doSelectPageInfo(() -> userInfoMapper.findList(record));
    }

    @Override
    public UserInfo getById(String id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(UserInfo record) {
        if (record.getId() == null) {
            record.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            record.setCreateTime(new Date());
            userInfoMapper.insert(record);
        } else {
            userInfoMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void deleteById(String id) {
        userInfoMapper.deleteByPrimaryKey(id);
    }
    
    
    
    
    
    @Override
    public UserInfo getByName(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(username);
        return userInfoMapper.selectOne(userInfo);
    }
    
    @Override
    public long cntByUserName(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        return PageHelper.count(() -> userInfoMapper.select(userInfo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String id) {
        userInfoMapper.deleteByPrimaryKey(id);
        SysUserRole userRole = new SysUserRole();
        userRole.setUid(id);
        userRoleMapper.delete(userRole);
    }

    @Override
    public boolean hasButtonPermission(String userId, String permission) {
        return userInfoMapper.hasButtonPermission(userId, permission) == null ? false : true;
    }
    
    
    
}
