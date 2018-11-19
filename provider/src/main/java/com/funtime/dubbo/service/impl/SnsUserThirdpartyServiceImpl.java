package com.funtime.dubbo.service.impl;

import com.funtime.dubbo.base.Constant;
import com.funtime.dubbo.dto.ThirdPartyUser;
import com.funtime.dubbo.entity.SnsUserThirdparty;
import com.funtime.dubbo.entity.User;
import com.funtime.dubbo.mapper.SnsUserThirdpartyMapper;
import com.funtime.dubbo.service.ISnsUserThirdpartyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.funtime.dubbo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * <p>
 * 第三方用户表 服务实现类
 * </p>
 *
 * @author Steven
 * @since 2018-09-21
 */
@Service(version = "1.0.0", timeout = 60000)
public class SnsUserThirdpartyServiceImpl extends ServiceImpl<SnsUserThirdpartyMapper, SnsUserThirdparty> implements ISnsUserThirdpartyService {
    @Autowired
    private IUserService userService;

    @Override
    public User insertThirdPartyUser(ThirdPartyUser param, String password) throws Exception{
        User sysUser = new User();
        sysUser.setPassWord(password);
        sysUser.setUserName("游客"+param.getOpenid());
        sysUser.setMobile(param.getOpenid());
        sysUser.setProfile(param.getAvatarUrl());
        User register = userService.register(sysUser, Constant.RoleType.USER);
        // 初始化第三方信息
        SnsUserThirdparty thirdparty = new SnsUserThirdparty();
        thirdparty.setProviderType(param.getProvider());
        thirdparty.setOpenId(param.getOpenid());
        thirdparty.setCreateDate(new Date());
        thirdparty.setUserNo(register.getUserNo());
        thirdparty.setStatus(String.valueOf(Constant.ENABLE));
        thirdparty.setAccessToken(param.getToken());
        this.insert(thirdparty);
        return register;
    }
}
