package com.onlytigi.common.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * web.xml 대체 
 * @author onlytigi
 */
public class WebConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootAppContext));
		
		AnnotationConfigWebApplicationContext springMvcContext = new AnnotationConfigWebApplicationContext();
		springMvcContext.register(SpringMvcConfig.class);
		  
		FilterRegistration.Dynamic filterReg = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filterReg.setInitParameter("encoding", "utf-8");
		filterReg.setInitParameter("forceEncoding", "true");
		filterReg.addMappingForUrlPatterns(null, true, "/*");
		
/*		<!-- 기존 web.xml에서 DelegatingFilterProxy 등록  -->
*		<filter>
*			<filter-name>springSecurityFilterChain</filter-name>
*			<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
*			</filter>
*		<filter-mapping>
*			<filter-name>springSecurityFilterChain</filter-name>
*			<url-pattern>/*</url-pattern>
*		</filter-mapping>
*/
		FilterRegistration.Dynamic delegatingFilterReg = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
		delegatingFilterReg.addMappingForUrlPatterns(null, true, "/*");
		
		ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcherServlet", new DispatcherServlet(springMvcContext));
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.addMapping("/");
	}
}