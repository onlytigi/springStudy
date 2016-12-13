package com.onlytigi.common.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Spring MVC Configuration
 * servlet-context.xml 대체
 * @author onlytigi
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.onlytigi.**.controller"}, 
		includeFilters = @Filter(value = {Controller.class, ControllerAdvice.class}), 
		useDefaultFilters = false)

public class SpringMvcConfig extends WebMvcConfigurerAdapter {

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").resourceChain(true);
    }
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true)
				  .useJaf(false)
				  .ignoreAcceptHeader(false)
				  .defaultContentType(MediaType.TEXT_HTML);
	}
	
	@Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
				
		InternalResourceViewResolver defaultViewResolver = new InternalResourceViewResolver();
		defaultViewResolver.setPrefix("/WEB-INF/views/");
		defaultViewResolver.setSuffix(".jsp");
		defaultViewResolver.setViewClass(JstlView.class);
		
		viewResolvers.add(defaultViewResolver);
		
        ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        contentViewResolver.setViewResolvers(viewResolvers);
        contentViewResolver.setContentNegotiationManager(manager);
        contentViewResolver.setOrder(0);
        return contentViewResolver;
	}
		
}
