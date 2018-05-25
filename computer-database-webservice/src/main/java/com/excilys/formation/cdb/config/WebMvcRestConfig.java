package com.excilys.formation.cdb.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
		"com.excilys.formation.cdb.config",
		"com.excilys.formation.cdb.security.config",
		"com.excilys.formation.cdb.persistence",
		"com.excilys.formation.cdb.persistence.dao.impl",
		"com.excilys.formation.cdb.service.impl",
		"com.excilys.formation.cdb.service.validators",
		"com.excilys.formation.cdb.service.paginator.pager",
		"com.excilys.formation.cdb.mapper.request",
		"com.excilys.formation.cdb.rest",
		"com.excilys.formation.cdb.rest.impl"

})
public class WebMvcRestConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}