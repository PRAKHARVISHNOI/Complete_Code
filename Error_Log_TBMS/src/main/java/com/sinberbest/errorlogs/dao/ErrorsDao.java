package com.sinberbest.errorlogs.dao;

import java.util.List;

import com.sinberbest.errorlogs.model.Errors;

public interface ErrorsDao {
	
	Errors addErrorLog(Errors errorLog, Integer deviceId);
	
	List<Errors> getAllErrorLogs();
}
