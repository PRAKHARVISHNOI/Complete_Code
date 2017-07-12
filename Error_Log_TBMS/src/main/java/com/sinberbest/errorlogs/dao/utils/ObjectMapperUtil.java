package com.sinberbest.errorlogs.dao.utils;

import com.sinberbest.api.entity.ErrorLog.ErrorLog;
import com.sinberbest.api.entity.common.ErrorCode;
import com.sinberbest.api.entity.common.Severity;
import com.sinberbest.errorlogs.model.Errors;

/**
 * 
 * @author Minal Bagade
 *
 */
public class ObjectMapperUtil {
	
	public static Errors getMappedObjectForAddErrorlog(ErrorLog errorLogWeb){
		
		Errors errorLog = new Errors();
		errorLog.setErrorCode(errorLogWeb.getErrorCode().toString());
		errorLog.setErrorTrace(errorLogWeb.getErrorTrace());
		errorLog.setSource(errorLogWeb.getSource());
		errorLog.setSeverity(errorLogWeb.getSeverity().toString());
		errorLog.setDetails(errorLogWeb.getDetails());
		
		return errorLog;
	}

	public static com.sinberbest.api.entity.common.ErrorLog getMappedObjectForError(Errors errorModel){
		
		 com.sinberbest.api.entity.common.ErrorLog error = new  com.sinberbest.api.entity.common.ErrorLog();
		error.setDeviceID(errorModel.getDevice().getDeviceTypeID());
		error.setErrorCode(ErrorCode.fromValue(errorModel.getErrorCode()));
		error.setErrorID(errorModel.getErrorId());
		error.setErrorTime(errorModel.getErrorTime().toString());
		error.setErrorTrace(errorModel.getErrorTrace());
		error.setSource(errorModel.getSource());
		error.setSeverity(Severity.fromValue(errorModel.getSeverity()));
		error.setDetails(errorModel.getDetails());
		
		return error;
				
	}
	
}
