package com.framework.core.platform.monitor.web.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author neo
 */
@XmlRootElement(name = "url_mappings")
@XmlAccessorType(XmlAccessType.FIELD)
public class URLMappings {
    @XmlElement(name = "mapping")
    private final List<URLMapping> mappings = new ArrayList<URLMapping>();

    public List<URLMapping> getMappings() {
        return mappings;
    }
}
