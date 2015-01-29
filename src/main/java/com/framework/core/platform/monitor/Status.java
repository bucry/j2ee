package com.framework.core.platform.monitor;

import com.framework.core.utils.StopWatch;
import com.framework.core.xml.XMLBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author neo
 */
public class Status {
    Map<String, ServiceDetail> serviceDetails = new HashMap<String, ServiceDetail>();

    public void check(List<ServiceMonitor> monitors) {
        for (ServiceMonitor monitor : monitors) {
            check(monitor);
        }
    }

    private void check(ServiceMonitor monitor) {
        StopWatch watch = new StopWatch();

        ServiceDetail detail = new ServiceDetail();
        try {
            detail.setStatus(monitor.getServiceStatus());
        } catch (Exception e) {
            detail.setErrorMessage(e.getClass().getName() + " " + e.getMessage());
            detail.setStatus(ServiceStatus.DOWN);
        } finally {
            detail.setElapsedTime(watch.elapsedTime());
        }

        serviceDetails.put(monitor.getServiceName(), detail);
    }

    public String toXML() {
        XMLBuilder builder = XMLBuilder.indentedXMLBuilder();
        builder.startElement("status");
        builder.textElement("server", ServiceStatus.UP.name());

        builder.startElement("services");
        for (Map.Entry<String, ServiceDetail> entry : serviceDetails.entrySet()) {
            builder.startElement("service");
            builder.attribute("name", entry.getKey());
            ServiceDetail detail = entry.getValue();
            builder.textElement("status", detail.getStatus().name());
            builder.textElement("elapsedTime", String.valueOf(detail.getElapsedTime()));
            builder.textElement("errorMessage", detail.getErrorMessage());
            builder.endElement();
        }
        builder.endElement();   // end of services

        builder.endElement();   // end of status

        return builder.toXML();
    }
}
