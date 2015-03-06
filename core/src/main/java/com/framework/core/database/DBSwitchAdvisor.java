package com.framework.core.database;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import com.framework.core.interceptor.AnnotatedMethodAdvisor;

public class DBSwitchAdvisor extends AnnotatedMethodAdvisor {

    public DBSwitchAdvisor(Class<? extends MethodInterceptor> interceptorClass) {
        super(Switch.class, interceptorClass);
    }

    @Override
    public Pointcut getPointcut() {
        return new StaticMethodMatcherPointcut() {
            public boolean matches(Method method, Class<?> targetClass) {
                return isAnnotationPresent(targetClass);
            }
        };
    }
    
    private boolean isAnnotationPresent(Class<?> targetClass) {
        Class<?> superClass = targetClass.getSuperclass();
        while (null != superClass && !superClass.equals(Object.class)) {
            if (superClass.equals(DBSwitch.class)) return true;
            superClass = superClass.getSuperclass();
        }
        return false;
    }

}