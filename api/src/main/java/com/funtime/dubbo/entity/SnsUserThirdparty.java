package com.funtime.dubbo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * <p>
 * 第三方用户表
 * </p>
 *
 */
@Data
@TableName("sns_user_thirdparty")
public class SnsUserThirdparty extends BaseEntity {
    /**
     * 用户ID
     */
	private String uid;
    /**
     * 第三方Id
     */
    @TableField("open_id")
	private String openId;
    /**
     * 绑定用户的id
     */
    @TableField("user_no")
	private String userNo;
    /**
     * 第三方token
     */
    @TableField("access_token")
	private String accessToken;
    /**
     * 第三方类型 qq:QQ 微信:WX 微博:SINA
     */
    @TableField("provider_type")
	private String providerType;
}
