package com.sinberbest.errorlogs.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;

public interface BaseDao {
	
	public Session getSession();
	
	public Criteria createCriteria(Class c);
	
}
