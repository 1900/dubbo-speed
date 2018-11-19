package com.funtime.dubbo.web;

import com.funtime.dubbo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected User getUserInfo(){
        return (User)SecurityUtils.getSubject().getPrincipal();
    }

}