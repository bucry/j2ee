package com.framework.core.platform.intercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Require Login
 * <p/>
 * Put to Controller class level or method level, Class level annotation takes
 * precedence
 * 
 * @author neo
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

    public LoginType value() default LoginType.Admmin;

    public enum LoginType {
        Admmin , Vendor
    }
}
