package com.lev4rec;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringMVCConfiguration implements WebMvcConfigurer {
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(false);
	}

	/*
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("http://localhost:8888/")
			.allowedOrigins("http://localhost:8080/xtext-service/update")
			.allowedMethods("PUT", "GET", "POST", "OPTIONS")
				.allowCredentials(false).maxAge(3600);
	}*/
	

	
	
	/*@Bean
	public FilterRegistrationBean processCorsFilter() {
	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	final CorsConfiguration config = new CorsConfiguration();
	config.setAllowCredentials(true);
	config.addAllowedOrigin("http://localhost:8888/xtext-service/update");
	config.addAllowedHeader("*");
	config.addAllowedMethod("PUT");
	config.addAllowedMethod("OPTIONS");
	source.registerCorsConfiguration("/**", config);


	final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	return bean;
	}*/
}
	