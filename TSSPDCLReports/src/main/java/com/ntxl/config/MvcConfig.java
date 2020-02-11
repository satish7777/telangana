package com.ntxl.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@EnableJpaAuditing
@PropertySource({"file:${app.config}/config.properties","file:${app.config}/application.properties"})
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public TemplateResolver emailTemplateResolver() {
	    TemplateResolver resolver = new ClassLoaderTemplateResolver();
	    resolver.setPrefix("templates/");
	    resolver.setSuffix(".html");
	    resolver.setTemplateMode("LEGACYHTML5");
	    resolver.setCharacterEncoding("UTF-8");
	    resolver.setOrder(1);
	    return resolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
	    final SpringTemplateEngine engine = new SpringTemplateEngine();
	    final Set<TemplateResolver> templateResolvers = new HashSet<TemplateResolver>();
	    templateResolvers.add(emailTemplateResolver());
	    engine.setTemplateResolvers(templateResolvers);
	    return engine;
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("accountsReport");
		registry.addViewController("/cdrReport").setViewName("cdrReport");
		registry.addViewController("/liveReport").setViewName("liveReport");
	}

}


