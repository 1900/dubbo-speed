package com.funtime.dubbo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * <p>
 * 验证码发送记录
 * </p>
 */
@Data
@TableName("sys_sms_verify")
public class SmsVerify extends BaseEntity {
    /**
     * 短信编号（可以自己生成，也可以第三方复返回）
     */
    @TableField("sms_id")
    private String smsId;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 验证码
     */
    @TableField("sms_verify")
    private String smsVerify;
    /**
     * 验证码类型（1：登录验证，2：注册验证，3：忘记密码，4：修改账号）
     */
    @TableField("sms_type")
    private Integer smsType;

    public SmsVerify(){}

    public SmsVerify(String smsId, String mobile, String smsVerify, Integer smsType, Long createDate) {
        this.smsId = smsId;
        this.mobile = mobile;
        this.smsVerify = smsVerify;
        this.smsType = smsType;
    }
}
