package com.funtime.dubbo.service;

import com.funtime.dubbo.dto.ThirdPartyUser;
import com.funtime.dubbo.entity.SnsUserThirdparty;
import com.baomidou.mybatisplus.service.IService;
import com.funtime.dubbo.entity.User;

/**
 * <p>
 * 第三方用户表 服务类
 * </p>
 *
 * @author Steven
 * @since 2018-09-21
 */
public interface ISnsUserThirdpartyService extends IService<SnsUserThirdparty> {
    User insertThirdPartyUser(ThirdPartyUser param, String password)throws Exception;

}
