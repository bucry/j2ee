package com.framework.core.collection;

import java.util.Date;
import com.framework.core.collection.converter.DateConverter;
import com.framework.core.collection.converter.NumberConverter;
import com.framework.core.utils.StringUtils;

/**
 * @author neo
 */
public class TypeConverter {
    static final String ERROR_MESSAGE_TARGET_CLASS_CANNOT_BE_PRIMITIVE = "targetClass cannot be primitive, use wrapper class instead, e.g. Integer.class for int.class";

    final DateConverter dateConverter = new DateConverter();
    final NumberConverter numberConverter = new NumberConverter();

    @SuppressWarnings("unchecked")
    public <T> T fromString(String textValue, Class<T> targetClass) {
        if (targetClass.isPrimitive())
            throw new IllegalArgumentException(ERROR_MESSAGE_TARGET_CLASS_CANNOT_BE_PRIMITIVE);

        if (String.class.equals(targetClass)) return (T) textValue;
        if (!StringUtils.hasText(textValue)) return null;

        if (Boolean.class.equals(targetClass)) return (T) Boolean.valueOf(textValue);

        if (Number.class.isAssignableFrom(targetClass))
            return (T) numberConverter.convertToNumber(textValue, (Class<? extends Number>) targetClass);

        if (Character.class.equals(targetClass)) return (T) Character.valueOf(textValue.charAt(0));

        if (Enum.class.isAssignableFrom(targetClass)) return (T) Enum.valueOf((Class<Enum>) targetClass, textValue);
        if (Date.class.equals(targetClass)) return (T) dateConverter.fromString(textValue);

        throw new TypeConversionException("not supported type, targetClass=" + targetClass.getName());
    }

    public String toString(Object value) {
        if (value == null) return "";
        if (value instanceof String) return (String) value;
        if (value instanceof Boolean) return String.valueOf(value);
        if (value instanceof Number) return String.valueOf(value);
        if (value instanceof Enum) return ((Enum) value).name();
        if (value instanceof Character) return String.valueOf(value);
        if (value instanceof Date) return dateConverter.toString((Date) value);

        throw new TypeConversionException("not supported type, targetClass=" + value.getClass().getName());
    }
}
