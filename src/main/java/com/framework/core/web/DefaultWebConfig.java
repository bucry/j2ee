package com.framework.core.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
public class DefaultWebConfig extends WebMvcConfigurerAdapter {


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
		MarshallingHttpMessageConverter jsonConverter = new MarshallingHttpMessageConverter();
		converters.add(jsonConverter);
		return converters;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.addAll(createMessageConverters());
	}


	@Override
	public void configureHandlerExceptionResolvers( List<HandlerExceptionResolver> exceptionResolvers) {
			
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		
	}

	@Override
	public void configureDefaultServletHandling( DefaultServletHandlerConfigurer config) {
			
		config.enable();
	}
}