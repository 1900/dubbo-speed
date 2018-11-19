/**
 * 第三方用户实体类
 */
package com.funtime.dubbo.dto;

import com.funtime.dubbo.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    /**
     * 用户信息
     */
    private User user;
    /**
     * 是否点赞: 0 否 1 是
     */
    private String like;
    /**
     * 是否关注: 0 否 1 是
     */
    private String follow;
    /**
     * 是否是好友: 0 否 1 是
     */
    private String isFriend;
}
