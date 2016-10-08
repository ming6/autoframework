package com.github.ming6.autoframework.springmvc.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import com.github.ming6.autoframework.springmvc.RequestBodyParamMethodArgumentResolver;
import com.github.ming6.autoframework.springmvc.WebMvcConfigurationSupport;

@ComponentScan(basePackages = "${scan.packages}",
	useDefaultFilters=false,
	includeFilters={
		@Filter(Controller.class),
		@Filter(RestController.class)
	}
)
public class SpringmvcConfig extends WebMvcConfigurationSupport {

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new RequestBodyParamMethodArgumentResolver());
	}
}