package com.funtime.dubbo.config.aspect;

public class RecordLogOperate extends AspectHandler{
    @Override
    protected RecordLogAspect factoryMethod() {
        return new RecordLogAspect();
    }
}
