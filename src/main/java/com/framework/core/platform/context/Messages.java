package com.framework.core.platform.context;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * @author neo
 */
public class Messages extends ResourceBundleMessageSource {
    public String getMessage(String key, Object... arguments) {
        return super.getMessage(key, arguments, Locale.getDefault());
    }
}
