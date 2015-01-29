package com.framework.core.platform.context;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.framework.core.exception.RuntimeIOException;

/**
 * @author neo
 */
public class PropertyContext extends PropertySourcesPlaceholderConfigurer {
    public Properties getAllProperties() {
        try {
            return mergeProperties();
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}
