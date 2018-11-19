package com.funtime.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.funtime.dubbo.mapper.UserMapper;
import com.funtime.dubbo.util.GenerationSequenceUtil;
import com.funtime.dubbo.util.JWTUtil;
import com.funtime.dubbo.dto.SysUserInfo;
import com.funtime.dubbo.dto.UserInfo;
import com.funtime.dubbo.entity.User;
import com.funtime.dubbo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author Steven
 * @since 2018-09-20
 */
@Service(version = "1.0.0", timeout = 60000)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String username) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("user_name={0}", username);
        return this.selectOne(ew);
    }

    @Override
    public User getUserByMobile(String mobile) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.eq("mobile", mobile);
        return this.selectOne(ew);
    }

    @Override
    public SysUserInfo getLoginUserByUserNo(String uid) {
        SysUserInfo sysUserInfo = new SysUserInfo();
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("id={0}", uid);
        User user = this.selectOne(ew);
        sysUserInfo.setUser(user);
        // 设置Token
        sysUserInfo.setToken(JWTUtil.sign(user.getUserNo(), user.getSalt()));
//        sysUserInfo.setToken(JWTUtil.sign(user.getUserNo(), user.getPassWord()));
        return sysUserInfo;
    }

    @Override
    public User register(User user, String roleCode) {
        String userNo = GenerationSequenceUtil.generateUUID("user");
        user.setUserNo(userNo);
        user.setCreateDate(new Date());
        user.setSalt(Base64.getEncoder().encodeToString(userNo.getBytes(StandardCharsets.UTF_8)));
        this.insert(user);
        return user;
    }

    @Override
    public Map<String, Object> getLoginUserInfo(User user) {
        Map<String, Object> result = new HashMap<>();
        // Token
        user.setToken(JWTUtil.sign(user.getUserNo(), user.getSalt()));
        // user.setToken(JWTUtil.sign(user.getUserNo(), user.getPassWord()));
        result.put("user", user);
        return result;
    }

    @Override
    public boolean deleteByUserNo(String userNo) {
        boolean resultUser = this.deleteById(userNo);
        return resultUser;
    }

    @Override
    public Page<User> selectPageByConditionUser(Page<User> userPage, String info, Integer[] status, String startTime, String endTime) {
        return null;
    }

    @Override
    public UserInfo getUserInfo(User user, String queryUserId) {
        UserInfo userInfo = new UserInfo();
        /**
         * 查询的用户
         */
        EntityWrapper<User> queryUser = new EntityWrapper<>();
        queryUser.where("id={0}", queryUserId);
        User qUser = this.selectOne(queryUser);

        userInfo.setUser(qUser);
        return userInfo;
    }

    @Override
    public UserInfo getCurrenUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        return userInfo;
    }

    @Override
    public Page<User> selectPageByRegion(Page<User> page, User user) {
        page.setRecords(userMapper.selectPageByRegion(page, user));
        return page;
    }

    @Override
    public Page<User> selectPageByUsers(Page<User> page, String users) {
        page.setRecords(userMapper.selectPageByUsers(page, users));
        return page;
    }
}