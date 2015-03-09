package com.web.app;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

@Configuration
@ComponentScan(basePackageClasses = AppConfig.class)
@EnableTransactionManagement
// The code equals aop config or provider annotation transaction.
@EnableAspectJAutoProxy
@PropertySource({ "classpath:site-jdbc.properties" })
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

	@Inject
	Environment env;
	
	/**
	 * 数据源
	 * @return
	 */
	@Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver.name"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.writedb.proxy.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }

	
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(
				dataSource()).scanPackages(AppConfig.class.getPackage()
				.getName());
		builder.setProperty(org.hibernate.cfg.Environment.DIALECT,
				MySQL5Dialect.class.getName());
		return builder.buildSessionFactory();
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
		textTypes.add(MediaType.APPLICATION_OCTET_STREAM);
		stringConverter.setSupportedMediaTypes(textTypes);
		converters.add(stringConverter);
		converters.add(new XmlAwareFormHttpMessageConverter());
		converters.add(new Jaxb2RootElementHttpMessageConverter());
		MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
		converters.add(jsonConverter);
		return converters;
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.addAll(createMessageConverters());
	}

	@Override
	public void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> exceptionResolvers) {

	}

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {

	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer config) {

		config.enable();
	}

	@Bean
	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		return localeResolver;
	}

	/**
	 * 定义spring MVC返回显示视图
	 * 
	 * @return
	 */
	@Bean
	public TilesViewResolver viewResolver() {
		return new TilesViewResolver();
	}

	/**
	 * 定义xml显示位置
	 * 
	 * @return
	 */
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "classpath*:config/tiles/page-tiles.xml" });
		return tilesConfigurer;
	}
	
}