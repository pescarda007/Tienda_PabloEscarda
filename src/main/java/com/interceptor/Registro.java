package com.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Registro implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new InterceptorAdmin())
			.addPathPatterns("/admin/**").excludePathPatterns("/static/**");
		
		registry.addInterceptor(new InterceptorEmple())
		.addPathPatterns("/emple/**")
		.excludePathPatterns("/static/**");
		registry.addInterceptor(new InterceptorUsuario())
		.addPathPatterns("/usuario/**").excludePathPatterns("/static/**");
	}

}
