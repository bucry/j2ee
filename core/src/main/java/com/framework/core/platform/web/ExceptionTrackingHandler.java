package com.framework.core.platform.web;

import com.framework.core.platform.monitor.ExceptionStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author neo
 */
public class ExceptionTrackingHandler implements HandlerExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(ExceptionTrackingHandler.class);
    private ExceptionStatistic exceptionStatistic;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        if (e != null) {
            //TODO: ignore input error?
            logger.error(e.getMessage(), e);
            exceptionStatistic.failed(e);
        }
        return null;
    }

    public void setExceptionStatistic(ExceptionStatistic exceptionStatistic) {
        this.exceptionStatistic = exceptionStatistic;
    }
}
