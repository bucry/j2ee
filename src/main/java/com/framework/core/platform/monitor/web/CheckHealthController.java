package com.framework.core.platform.monitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.platform.monitor.Pass;
import com.framework.core.platform.monitor.web.view.SiteHealth;
import com.framework.core.platform.web.rest.FrameWorkRestController;

@Controller
@Pass
public class CheckHealthController extends FrameWorkRestController {
    
    @RequestMapping(value = "/checkhealth", produces = "application/xml", method = RequestMethod.GET)
    @ResponseBody
    public SiteHealth checkHealth() {
        SiteHealth siteHealth = new SiteHealth();
        siteHealth.setSiteStatus("UP");
        return siteHealth;
    }
    
}