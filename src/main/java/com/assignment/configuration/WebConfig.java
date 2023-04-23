package com.assignment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@ComponentScan
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/js/**")
				.addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/*.html")
				.addResourceLocations("classpath:/static/");
	}

}