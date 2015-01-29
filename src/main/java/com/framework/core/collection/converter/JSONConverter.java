package com.framework.core.collection.converter;

import com.framework.core.json.JSONBinder;

public class JSONConverter {
    public <T> T fromString(Class<T> targetClass, String value) {
        return JSONBinder.binder(targetClass).fromJSON(value);
    }

    @SuppressWarnings("unchecked")
    public <T> String toString(T value) {
        return JSONBinder.binder((Class<T>) value.getClass()).toJSON(value);
    }
}
