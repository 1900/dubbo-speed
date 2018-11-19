package com.funtime.dubbo.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.funtime.dubbo.dto.SysUserInfo;
import com.funtime.dubbo.dto.UserInfo;
import com.funtime.dubbo.entity.User;

import java.util.Map;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author Steven
 * @since 2018-09-20
 */
public interface IUserService extends IService<User> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User getUserByUserName(String username);

    User getUserByMobile(String mobile);

    SysUserInfo getLoginUserByUserNo(String userNo);

    /**
     * 注册用户
     *
     * @param user
     * @param roleCode
     * @return
     */
    User register(User user, String roleCode);

    Map<String, Object> getLoginUserInfo(User user);

    boolean deleteByUserNo(String userNo);

    Page<User> selectPageByConditionUser(Page<User> userPage, String info, Integer[] status, String startTime, String endTime);


    /**
     * 获取用户详情
     *
     * @param user 用户本身
     * @return queryUserNo 需要查询的用户
     */
    UserInfo getUserInfo(User user, String queryUserNo);

    /**
     * 自己获取自己的详情
     *
     * @param user 用户本身
     * @return
     */
    UserInfo getCurrenUserInfo(User user);

    Page<User> selectPageByRegion(Page<User> page, User user);

    Page<User> selectPageByUsers(Page<User> page, String users);
}
