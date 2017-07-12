package com.sinberbest.errorlogs.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sinberbest.errorlogs.dao.ErrorsDao;
import com.sinberbest.errorlogs.exception.DataPersistenceException;
import com.sinberbest.errorlogs.model.Device;
import com.sinberbest.errorlogs.model.Errors;

/**
 * 
 * @author Minal Bagade
 *
 */
@Repository("errorsDao")
public class ErrorsDaoImpl extends BaseDaoImpl implements ErrorsDao{
	
	private final Logger sLogger = LoggerFactory.getLogger(ErrorsDaoImpl.class);

	@Override
	public Errors addErrorLog(Errors errorLog, Integer deviceId) {
		try {
			Device device = (Device) getSession().get(Device.class, deviceId);
			if(device!=null){
				errorLog.setDevice(device);
				device.getErrors().add(errorLog);
				getSession().save(errorLog);
			}else{
				throw new DataPersistenceException("api.errorlog.save.error");
			}
		} catch (Exception ex) {
			sLogger.error(ex.getMessage());
			throw new DataPersistenceException("api.errorlog.save.error", ex);
		}
		return errorLog;
	}

	
	@Override
	public List<Errors> getAllErrorLogs() {
		Criteria criteria = null;
		try {
			criteria = getSession().createCriteria(Errors.class);
		} catch (Exception ex) {
			sLogger.error(ex.getMessage());
			throw new DataPersistenceException("api.errorlog.retrieve.eror", ex);
		}
		return criteria.list();
	}

}
