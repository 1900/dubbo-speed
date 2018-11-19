package com.funtime.dubbo.config.shiro;

/**
 * Created by steven on 2018/9/21.
 */

import com.funtime.dubbo.config.annotation.CurrentUser;
import com.funtime.dubbo.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CurrentUserAuthMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User user = null;
        try {
            user = (User) webRequest.getAttribute("CUR_USERAUTH", RequestAttributes.SCOPE_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}

