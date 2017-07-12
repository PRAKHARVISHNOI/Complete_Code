package com.sinberbest.errorlogs.exception.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinberbest.api.entity.types.ErrorLogResponse;
import com.sinberbest.api.entity.types.GenericAPIErrorResponse;
import com.sinberbest.errorlogs.constants.ErrorsAPIConstants;

/**
 * An abstract parent class for all ErrorLogging API exception handler classes.
 * 
 * @author Minal Bagade
 */
public abstract class AbstractApiExceptionHandler {

    /**
     * Logger Instance
     */
    private final Logger sLogger = LoggerFactory.getLogger(AbstractApiExceptionHandler.class);
    
    /**
     * This method will create an return an instance of GenericAPIErrorResponse.
     * 
     * @return ErrorLogResponse instance
     */
    protected ErrorLogResponse getGenericAPIErrorResponse() {
    	ErrorLogResponse errorlogAPIResponse = new GenericAPIErrorResponse();
        errorlogAPIResponse.setErrorCode(ErrorsAPIConstants.GENERIC_ERROR_CODE);
        return errorlogAPIResponse;
    }
}
