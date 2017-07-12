package com.sinberbest.errorlogs.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/*
*//**
 * This class registers CORS mapping
 * 
 * @author Minal Bagade
 *
 *//*
*/@Configuration
//@EnableWebMvc  
@ImportResource({"classpath:META-INF/web-application-config.xml"})
public class WebConfig extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer {
   

	@Autowired
    @Qualifier("jaxb2Marshaller")        
    Jaxb2Marshaller jaxb2Marshaller;
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
		
	/**//**
     * This method is for mapping of errors
     *//*
*/    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {        
        container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405"));
        //container.addErrorPages(new ErrorPage("/error/405"));
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
        //container.addErrorPages(new ErrorPage("/error/404"));
    }
}
