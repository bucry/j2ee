package com.framework.core.collection.converter;

import java.util.Date;
import com.framework.core.collection.TypeConversionException;
import com.framework.core.utils.Convert;

/**
 * @author neo
 */
public class DateConverter {
    public Date fromString(String property) {
        return parseDateTime(property);
    }

    public String toString(Date value) {
        return Convert.toString(value, Convert.DATE_FORMAT_DATETIME);
    }

    Date parseDateTime(String textValue) {
        Date date = Convert.toDateTime(textValue, null);
        if (date == null)
            date = Convert.toDate(textValue, null);
        if (date == null)
            throw new TypeConversionException("can not convert to date, text=" + textValue);
        return date;
    }
}
