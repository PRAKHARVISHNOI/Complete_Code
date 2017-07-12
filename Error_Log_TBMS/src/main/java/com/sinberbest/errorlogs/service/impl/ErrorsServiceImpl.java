package com.sinberbest.errorlogs.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinberbest.errorlogs.dao.ErrorsDao;
import com.sinberbest.errorlogs.dao.impl.ErrorsDaoImpl;
import com.sinberbest.errorlogs.exception.DataPersistenceException;
import com.sinberbest.errorlogs.model.Errors;
import com.sinberbest.errorlogs.service.ErrorsService;

/**
 * 
 * @author Minal Bagade
 *
 */
@Service("errorsService")
@Transactional
public class ErrorsServiceImpl implements ErrorsService{

	@Autowired
	ErrorsDao errorsDao;
	private final Logger sLogger = LoggerFactory.getLogger(ErrorsServiceImpl.class);
	@Override
	public Errors addErrorLog(Errors errorLog, Integer deviceId) {
		try{
		return errorsDao.addErrorLog(errorLog, deviceId);
		}
		catch(DataPersistenceException ex)
		{
			sLogger.error("In ErrorsServiceImpl"+ex.getMessage());
			throw ex;
		}
		
	}

	@Override
	public List<Errors> getAllErrorLogs() {

		return errorsDao.getAllErrorLogs();
		
	}
	
	
}
