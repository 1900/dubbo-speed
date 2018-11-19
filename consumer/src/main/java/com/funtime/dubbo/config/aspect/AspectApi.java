package com.funtime.dubbo.config.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;

public interface AspectApi {
     Object doHandlerAspect(Object[] obj, ProceedingJoinPoint pjp, Method method, boolean isAll)throws Throwable;
}
