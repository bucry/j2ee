package com.framework.core.platform.web;

import com.framework.core.collection.converter.JSONConverter;
import com.framework.core.platform.context.Messages;
import org.springframework.context.i18n.LocaleContextHolder;
import javax.inject.Inject;
import java.util.Locale;

/**
 * @author neo
 */
public class DefaultController implements SpringController {

    protected Messages messages;
    
    protected JSONConverter jsonConverter;

    public <T> T fromString(Class<T> targetClass, String value) {
        return jsonConverter.fromString(targetClass, value);
    }

    protected <T> String toString(T value) {
        return jsonConverter.toString(value);
    }

    protected String getMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messages.getMessage(messageKey, args, locale);
    }

    @Inject
    public void setMessages(Messages messages) {
        this.messages = messages;
    }
    
    @Inject
    public void setjSONConverter(JSONConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }
}