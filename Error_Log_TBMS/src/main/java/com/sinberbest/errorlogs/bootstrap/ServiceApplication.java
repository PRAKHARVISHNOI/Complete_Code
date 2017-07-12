	package com.sinberbest.errorlogs.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.sinberbest.errorlogs.controller.rest.ErrorLogsController;


/**
 * Spring Application start-up
 * @author Minal Bagade
 */
@SpringBootApplication(scanBasePackageClasses = {ErrorLogsController.class})
@ComponentScan(basePackages = "com.sinberbest.errorlogs")
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
