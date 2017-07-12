package com.sinberbest.errorlogs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinberbest.errorlogs.dao.BaseDao;

/**
 * 
 * @author Minal Bagade
 *
 */
public class BaseDaoImpl implements BaseDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	
	public Criteria createCriteria(Class c){
		return getSession().createCriteria(c);
	}
}
