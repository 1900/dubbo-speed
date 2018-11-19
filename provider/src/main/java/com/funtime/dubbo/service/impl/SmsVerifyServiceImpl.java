package com.funtime.dubbo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.funtime.dubbo.entity.SmsVerify;
import com.funtime.dubbo.mapper.SmsVerifyMapper;
import com.funtime.dubbo.service.ISmsVerifyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * <p>
 * SMS发送记录表 服务实现类
 * </p>
 *
 * @author Steven
 * @since 2018-09-21
 */
@Service(version = "1.0.0", timeout = 60000)
public class SmsVerifyServiceImpl extends ServiceImpl<SmsVerifyMapper, SmsVerify> implements ISmsVerifyService {

    @Override
    public List<SmsVerify> getByMobileAndCaptchaAndType(String mobile, String captcha, int type) {
        EntityWrapper<SmsVerify> smsQuery = new EntityWrapper<>();
        smsQuery.where("mobile={0} and sms_verify={1} and  sms_type = {2}",
                mobile, captcha, type);
        smsQuery.orderBy("create_date", false);
        return this.selectList(smsQuery);
    }
}
