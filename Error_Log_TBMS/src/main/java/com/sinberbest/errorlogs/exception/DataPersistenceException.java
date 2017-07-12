package com.sinberbest.errorlogs.exception;

import com.sinberbest.api.entity.types.ErrorLogResponse;

public class DataPersistenceException extends BaseRuntimeException{
	
	private String errorCode;
    
    private Object[] args;
    
    private ErrorLogResponse errorLogResponse;
    
    private Throwable cause;
    
    public DataPersistenceException(String errorCode, Object[] args, ErrorLogResponse errorLogResponse,
			Throwable cause) {
		super();
		this.errorCode = errorCode;
		this.args = args;
		this.errorLogResponse = errorLogResponse;
		this.cause = cause;
	}

	public DataPersistenceException() {
    }

    public DataPersistenceException(String message) {
        super(message);
    }
    
    public DataPersistenceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DataPersistenceException(String message, Throwable cause) {
        super(message, cause);
        this.cause=cause;
    }

    public DataPersistenceException(Throwable cause) {
        super(cause);
        this.cause=cause;
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

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

    
}
