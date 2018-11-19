package com.funtime.dubbo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 */
@Data
@TableName("sys_user")
public class User extends BaseEntity {
    /**
     * 用户NO
     */
    @TableField("user_no")
    private String userNo;
    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 密码
     */
    @TableField("pass_word")
    @JSONField(serialize = false)
    private String passWord;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 性别
     */
    private String gender;
    /**
     * 签名
     */
    private String signature;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String profile;
    /**
     * 来源
     */
    private String source;

    /**
     * Token
     */
    @TableField(exist = false)
    private String token;
}
