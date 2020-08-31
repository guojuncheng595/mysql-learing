package com.gjc.dydatasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @program: mysql-master-slave
 * @description: 数据源AOP
 * @author: gjc
 * @create: 2020-08-31 12:29
 **/
@Slf4j
@Aspect
@Order(value = 1)
@Component
public class DataSourceContextAop {

    @Around("@annotation(com.gjc.dydatasource.DataSourceSelector)")
    public Object setDynamicDataSource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean clear = true;
        try {
            Method method = this.getMethod(proceedingJoinPoint);
            DataSourceSelector dataSourceSelector = method.getAnnotation(DataSourceSelector.class);
            clear = dataSourceSelector.clear();
            DataSourceContextHolder.set(dataSourceSelector.value().getDataSourceName());
            log.info("=========数据源切换到：{}", dataSourceSelector.value().getDataSourceName());
            return proceedingJoinPoint.proceed();
        } finally {
            if (clear) {
                DataSourceContextHolder.clear();
            }
        }
    }

    private Method getMethod(JoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }

}
