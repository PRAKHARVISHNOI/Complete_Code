package com.sinberbest.errorlogs.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinberbest.errorlogs.constants.ErrorsAPIConstants;
import com.sinberbest.errorlogs.exception.ResourceNotFoundException;

/**
 * 
 * @author Minal Bagade
 *
 */
@RestController
@RequestMapping(value = "/error")
public class ErrorHandlerController{
    
    /**
     * This resource method is used to handle HTTP status 404 error.
     */
    @RequestMapping(value = "/404")
    public void resourceNotFound() {
        String messageNotFoundErrorMessageKey = "api.resource.not.found";
        throw new ResourceNotFoundException(messageNotFoundErrorMessageKey, 
                ErrorsAPIConstants.HTTP_ERROR_CODE);
    }
    
    /**
     * This resource method is used to handle HTTP status 405 error.
     * 
     * @param httpReqquest
     * @throws HttpRequestMethodNotSupportedException 
     */
    @RequestMapping(value = "/405")
    public void requestMethodNotSupported(HttpServletRequest httpReqquest) throws HttpRequestMethodNotSupportedException {
        String requestMethodNotSupportedErrorMessageKey = "api.method.not.supported";
        String requestMethod = httpReqquest.getMethod() != null ? httpReqquest.getMethod() : null;
        throw new HttpRequestMethodNotSupportedException(requestMethod, requestMethodNotSupportedErrorMessageKey);
    }
}
