package com.sinberbest.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


/**
 * Spring Application start-up
 * @author Minal Bagade
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.sinberbest.poc")
public class ServiceApplication extends SpringBootServletInitializer{
 
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
	
	
	/**
     * Initializes this application when running in a servlet container (e.g. Tomcat)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServiceApplication.class);
    }
	
}
