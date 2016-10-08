package com.github.ming6.autoframework.springmvc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.support.CompositeUriComponentsContributor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class WebMvcConfigurationSupport extends org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		super.setApplicationContext(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public CompositeUriComponentsContributor mvcUriComponentsContributor() {
		RequestMappingHandlerAdapter adapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
		return new CompositeUriComponentsContributor(adapter.getArgumentResolvers(), mvcConversionService());
	}

}