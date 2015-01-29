package com.framework.core.collection.converter;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.framework.core.collection.TypeConversionException;

/**
 * @author neo
 */
public class NumberConverter {
    public Number convertToNumber(String value, Class<? extends Number> targetClass) {
        if (Integer.class.equals(targetClass)) return Integer.valueOf(value);
        if (Double.class.equals(targetClass)) return Double.valueOf(value);
        if (Long.class.equals(targetClass)) return Long.valueOf(value);
        if (Float.class.equals(targetClass)) return Float.valueOf(value);
        if (Short.class.equals(targetClass)) return Short.valueOf(value);
        if (Byte.class.equals(targetClass)) return Byte.valueOf(value);
        if (BigDecimal.class.equals(targetClass)) return new BigDecimal(value);
        if (BigInteger.class.equals(targetClass)) return new BigInteger(value);

        throw new TypeConversionException("unsupported number type, targetClass=" + targetClass.getName() + ", value=" + value);
    }
}
