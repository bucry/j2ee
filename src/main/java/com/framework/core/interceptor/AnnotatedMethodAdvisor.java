package com.framework.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

/**
 * @author bfc
 */
public class AnnotatedMethodAdvisor implements PointcutAdvisor {
    private final Class<? extends Annotation> annotationClass;
    //private final Class<? extends MethodInterceptor> interceptorClass;
    private MethodInterceptor interceptor;

    public AnnotatedMethodAdvisor(Class<? extends Annotation> annotationClass, Class<? extends MethodInterceptor> interceptorClass) {
        this.annotationClass = annotationClass;
        //this.interceptorClass = interceptorClass;
    }

    @PostConstruct
    public void initialize() {
    	
    }

    public Pointcut getPointcut() {
        return new StaticMethodMatcherPointcut() {
            public boolean matches(Method method, Class<?> targetClass) {
                return method.isAnnotationPresent(annotationClass);
            }
        };
    }

    public Advice getAdvice() {
        return interceptor;
    }

    public boolean isPerInstance() {
        return true;
    }
}