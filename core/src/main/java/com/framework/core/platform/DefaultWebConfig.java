package com.framework.core.platform;

import com.framework.core.json.JSONBinder;
import com.framework.core.platform.context.Messages;
import com.framework.core.platform.monitor.ExceptionStatistic;
import com.framework.core.platform.monitor.web.CheckHealthController;
import com.framework.core.platform.monitor.web.ExceptionController;
import com.framework.core.platform.monitor.web.MemoryUsageController;
import com.framework.core.platform.monitor.web.StatusController;
import com.framework.core.platform.monitor.web.ThreadInfoController;
import com.framework.core.platform.monitor.web.URLMappingController;
import com.framework.core.platform.web.ExceptionTrackingHandler;
import com.framework.core.platform.web.form.AnnotationFormArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author neo
 */
@EnableWebMvc
public class DefaultWebConfig extends WebMvcConfigurerAdapter {
    @Inject
    Messages messages;

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messages);
        validator.afterPropertiesSet();
        return validator;
    }

    private List<HttpMessageConverter<?>> createMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new ByteArrayHttpMessageConverter());
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);
        ArrayList<MediaType> textTypes = new ArrayList<MediaType>();
        textTypes.add(MediaType.TEXT_PLAIN);
        textTypes.add(MediaType.TEXT_HTML);
        textTypes.add(MediaType.TEXT_XML);
        textTypes.add(MediaType.APPLICATION_XML);
        stringConverter.setSupportedMediaTypes(textTypes);
        converters.add(stringConverter);
        converters.add(new XmlAwareFormHttpMessageConverter());
        converters.add(new Jaxb2RootElementHttpMessageConverter());
       /* MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        jsonConverter.setObjectMapper(JSONBinder.createMapper());
        converters.add(jsonConverter);*/
        return converters;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.addAll(createMessageConverters());
    }

    @Inject
    ExceptionStatistic exceptionStatistic;

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        ExceptionTrackingHandler exceptionTrackingHandler = new ExceptionTrackingHandler();
        exceptionTrackingHandler.setExceptionStatistic(exceptionStatistic);
        exceptionResolvers.add(exceptionTrackingHandler);

        ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
        exceptionHandlerExceptionResolver.setMessageConverters(createMessageConverters());
        exceptionHandlerExceptionResolver.afterPropertiesSet();
        exceptionResolvers.add(exceptionHandlerExceptionResolver);

        exceptionResolvers.add(new ResponseStatusExceptionResolver());
        exceptionResolvers.add(new DefaultHandlerExceptionResolver());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AnnotationFormArgumentResolver());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer config) {
        config.enable();
    }

    @Bean
    public ExceptionController exceptionController() {
        return new ExceptionController();
    }

    @Bean
    public StatusController statusController() {
        return new StatusController();
    }

    @Bean(name = "URLMappingController")
    public URLMappingController urlMappingController() {
        return new URLMappingController();
    }

    @Bean
    public ThreadInfoController threadInfoController() {
        return new ThreadInfoController();
    }

    @Bean
    public MemoryUsageController memoryUsageController() {
        return new MemoryUsageController();
    }
    
    @Bean
    public CheckHealthController checkHealthController() {
        return new CheckHealthController();
    }
    
}