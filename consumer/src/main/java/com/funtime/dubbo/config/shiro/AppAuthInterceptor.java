package com.funtime.dubbo.config.shiro;

/**
 * Created by steven on 2018/9/21.
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.funtime.dubbo.base.BusinessException;
import com.funtime.dubbo.base.PublicResultConstant;
import com.funtime.dubbo.config.annotation.Pass;
import com.funtime.dubbo.config.exception.UnauthorizedException;
import com.funtime.dubbo.entity.User;
import com.funtime.dubbo.service.IUserService;
import com.funtime.dubbo.util.DateTimeUtil;
import com.funtime.dubbo.util.JWTUtil;
import com.funtime.dubbo.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

@Component
public class AppAuthInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(AppAuthInterceptor.class);
    @Reference(version = "1.0.0")
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();
//        log.debug("requestIp: " + getIpAddress(request));
        log.debug("Method: " + method.getName() + ", IgnoreSecurity: " + method.isAnnotationPresent(Pass.class));
        log.debug("requestPath: " + requestPath);
        if (method.isAnnotationPresent(Pass.class)) {
            return true;
        }
        String token = request.getHeader("ACCESS_TOKEN");
        log.info("Interceptor ACCESS_TOKEN:{}", token);
        if (!StringUtils.isNotEmpty(token)) {
            throw new BusinessException(PublicResultConstant.FAILED.toString());
        }

        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            throw new UnauthorizedException("token invalid");
        }

        User userBean = userService.selectOne(new EntityWrapper<User>().where("user_no = {0} and status = 0", userNo));

        if (userBean == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
        if (! JWTUtil.verify(token, userNo, userBean.getSalt())) {
            throw new UnauthorizedException("Username or password error");
        }

        log.info("Interceptor CUR_USERAUTH:{}", JSON.toJSON(userNo));
        request.setAttribute("CUR_USERAUTH", userBean);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
}