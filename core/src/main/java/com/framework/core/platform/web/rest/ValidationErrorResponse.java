package com.framework.core.platform.web.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author neo
 */
@XmlRootElement(name = "validation_error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationErrorResponse {
        
    @XmlElementWrapper(name = "field_errors")
    @XmlElement(name = "field_error")
    private final List<FieldError> fieldErrors = new ArrayList<FieldError>();

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

}