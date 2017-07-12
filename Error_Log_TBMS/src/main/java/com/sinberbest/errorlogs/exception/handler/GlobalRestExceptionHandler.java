package com.sinberbest.errorlogs.exception.handler;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinberbest.api.entity.types.ErrorLogResponse;
import com.sinberbest.errorlogs.constants.ErrorsAPIConstants;
import com.sinberbest.errorlogs.exception.DataPersistenceException;

@ControllerAdvice(basePackages = "com.sinberbest.transactions.controller")
@PropertySource(value = { "classpath:messages.properties" })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestExceptionHandler extends AbstractApiExceptionHandler{
	
	private final Logger sLogger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);
    
    @Autowired
    private MessageSource messageSource;
    
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({DataPersistenceException.class})
    public @ResponseBody
    ErrorLogResponse handleDataPersistenceException(HttpServletRequest req, 
    		DataPersistenceException dpex) {
    	ErrorLogResponse ErrorLogResponse = dpex.getErrorLogResponse();
        if (ErrorLogResponse == null) {
            ErrorLogResponse = getGenericAPIErrorResponse();
        }
        
        // If errorCode is not set in exception, set the GENERIC one.
        String errorCode = dpex.getErrorCode() != null ? dpex.getErrorCode() 
                : ErrorsAPIConstants.DATABASE_ERROR_CODE;
        ErrorLogResponse.setErrorCode(errorCode);
        
        String errorMessageKey = dpex.getMessage();
        //set default message
        ErrorLogResponse.setMessageEn(messageSource.getMessage("api.generic.data.persistence.error", new Object[]{req.getRequestURI()}, Locale.getDefault()));
        if (errorMessageKey != null) {
        	ErrorLogResponse.setMessageEn(messageSource.getMessage(errorMessageKey, new Object[]{req.getRequestURI()}, Locale.getDefault()));
            
            } 
        
        return ErrorLogResponse;
    }

}
