package com.funtime.dubbo.config.aspect;

public class ValidationParamOperate extends AspectHandler{
    @Override
    protected ValidationParamAspect factoryMethod() {
        return  new ValidationParamAspect();
    }
}
