package com.sinberbest.errorlogs.exception;

import com.sinberbest.api.entity.types.ErrorLogResponse;

/**
 * 
 * @author Minal Bagade
 *
 */
public class ResourceNotFoundException extends BaseRuntimeException {
    
    private String errorCode;
    
    private Object[] args;
    
    private ErrorLogResponse errorLogResponse;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public ResourceNotFoundException(ErrorLogResponse errorlogsResponse, 
            String message, Object[] args, String errorCode) {
        super(message);
        this.errorLogResponse = errorLogResponse;
        this.errorCode = errorCode;
        this.args = args;
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

	public ErrorLogResponse getErrorLogResponse() {
		return errorLogResponse;
	}

	public void setErrorLogResponse(ErrorLogResponse errorLogResponse) {
		this.errorLogResponse = errorLogResponse;
	}

   
}