package com.sinberbest.errorlogs.service;

import java.util.List;

import com.sinberbest.errorlogs.model.Errors;

public interface ErrorsService {
	
	Errors addErrorLog(Errors errorLog, Integer deviceId);
	
	List<Errors> getAllErrorLogs();

}
