package com.funtime.dubbo.dto;

import com.funtime.dubbo.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserInfo implements Serializable {
    private User user;
    private String token;
    private String isNewUser; // 0 否,1是
}
