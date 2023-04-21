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
		registry.addResourceHandler("/css/**")
				.addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/img/**")
				.addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/fonts/**")
				.addResourceLocations("classpath:/static/fonts/");
		registry.addResourceHandler("/icon-fonts/**")
				.addResourceLocations("classpath:/static/icon-fonts/");
		registry.addResourceHandler("/scss/**")
				.addResourceLocations("classpath:/static/scss/");
		registry.addResourceHandler("/*.html")
				.addResourceLocations("classpath:/static/");
	}

}