package com.sinberbest.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This class registers CORS mapping
 * 
 * @author Minal Bagade
 *
 */
@Configuration
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


	@Override
	public void customize(ConfigurableEmbeddedServletContainer arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
