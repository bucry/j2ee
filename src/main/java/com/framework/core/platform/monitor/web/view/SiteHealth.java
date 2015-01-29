package com.framework.core.platform.monitor.web.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "site_health")
@XmlAccessorType(XmlAccessType.FIELD)
public class SiteHealth {
    
    @XmlElement(name = "site_status")
    private String siteStatus;

    public String getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(String siteStatus) {
        this.siteStatus = siteStatus;
    }
}