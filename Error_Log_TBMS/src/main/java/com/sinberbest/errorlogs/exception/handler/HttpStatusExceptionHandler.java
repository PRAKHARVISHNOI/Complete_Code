package com.sinberbest.errorlogs.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinberbest.api.entity.types.ErrorLogResponse;
import com.sinberbest.errorlogs.constants.ErrorsAPIConstants;
import com.sinberbest.errorlogs.exception.ResourceNotFoundException;


/**
 * 
 * HttpStatus.SERVICE_UNAVAILABLE (503)
 * HttpStatus.METHOD_NOT_ALLOWED (405)
 * HttpStatus.NOT_FOUND (404)
 * 
 * @author Minal Bagade
 */
@ControllerAdvice(basePackages = "com.sinberbest.errorlogs.controller")
@PropertySource(value = { "classpath:messages.properties" })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpStatusExceptionHandler extends AbstractApiExceptionHandler {

    
    private final Logger sLogger = LoggerFactory.getLogger(HttpStatusExceptionHandler.class);
    
    @Autowired
    private Environment environment;
          
    //TODO, check the exception generators and map the method again
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public @ResponseBody
    ErrorLogResponse requestMethodNotSupportedException(HttpServletRequest req, 
            HttpRequestMethodNotSupportedException rmnsex) {
    	ErrorLogResponse errorlogResponse = getGenericAPIErrorResponse();
        
    	errorlogResponse.setErrorCode(ErrorsAPIConstants.HTTP_ERROR_CODE);
    	errorlogResponse.setMessageEn(environment.getProperty("api.method.not.supported")+ req.getMethod());
    	environment.resolveRequiredPlaceholders(req.getMethod());
        return errorlogResponse;
    }
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public @ResponseBody
    ErrorLogResponse handleResourceNotFoundException(HttpServletRequest req, 
            ResourceNotFoundException rnfex) {
    	ErrorLogResponse errorlogResponse = rnfex.getErrorLogResponse();
        if (errorlogResponse == null) {
            errorlogResponse = getGenericAPIErrorResponse();
        }
        
        // If errorCode is not set in exception, set the GENERIC one.
        String errorCode = rnfex.getErrorCode() != null ? rnfex.getErrorCode() 
                : ErrorsAPIConstants.HTTP_ERROR_CODE;
        errorlogResponse.setErrorCode(errorCode);
        
        String errorMessageKey = rnfex.getMessage();
        //set default message
        errorlogResponse.setMessageEn("Resources not found for URL : " + req.getRequestURI());
        if (errorMessageKey != null) {
        	 errorlogResponse.setMessageEn(environment.getProperty(errorMessageKey, req.getRequestURI()));
            
            } 
        
        return errorlogResponse;
    }
    
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({InterruptedException.class,IllegalArgumentException.class})
    public @ResponseBody
    ErrorLogResponse handleInterruptedException(HttpServletRequest req, 
    		Exception intEx) {
    	ErrorLogResponse errorlogResponse = getGenericAPIErrorResponse();
        errorlogResponse.setErrorCode(ErrorsAPIConstants.GENERIC_ERROR_CODE);
        
        //set default message
        errorlogResponse.setMessageEn("Unable to process request : " + intEx.getMessage());
        
        return errorlogResponse;
    }
   
}