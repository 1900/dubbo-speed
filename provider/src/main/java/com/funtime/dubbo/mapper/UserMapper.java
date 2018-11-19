package com.funtime.dubbo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.funtime.dubbo.dto.UserInfo;
import com.funtime.dubbo.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author Steven
 * @since 2018-09-20
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User getUserByUserName(String username);

    User getUserByMobile(String mobile);

    /**
     * 注册用户
     *
     * @param user
     * @param roleCode
     * @return
     */
    User register(User user, String roleCode);

    Map<String, Object> getLoginUserAndMenuInfo(User user);

    boolean deleteByUserNo(String userNo);

    Page<User> selectPageByConditionUser(Page<User> userPage, String info, Integer[] status, String startTime, String endTime);

    List<User> selectPageByRegion(Page<User> page, User user);

    /**
     * 获取用户详情
     *
     * @param user
     * @return
     */
    UserInfo getUserInfo(User user);

    List<User> selectPageByUsers(Page<User> page, String users);
}
