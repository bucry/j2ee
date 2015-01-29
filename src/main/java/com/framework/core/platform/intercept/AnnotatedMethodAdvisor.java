package com.framework.core.platform.intercept;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import com.framework.core.platform.SpringObjectFactory;

/**
 * @author neo
 */
public class AnnotatedMethodAdvisor implements PointcutAdvisor {
    private final Class<? extends Annotation> annotationClass;
    private final Class<? extends MethodInterceptor> interceptorClass;
    private SpringObjectFactory objectFactory;
    private MethodInterceptor interceptor;

    public AnnotatedMethodAdvisor(Class<? extends Annotation> annotationClass, Class<? extends MethodInterceptor> interceptorClass) {
        this.annotationClass = annotationClass;
        this.interceptorClass = interceptorClass;
    }

    @PostConstruct
    public void initialize() {
        interceptor = objectFactory.createBean(interceptorClass);
    }

    @Override
    public Pointcut getPointcut() {
        return new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return method.isAnnotationPresent(annotationClass);
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return interceptor;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    @Inject
    public void setObjectFactory(SpringObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }
    
}