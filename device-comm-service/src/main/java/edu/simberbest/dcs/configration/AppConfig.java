package edu.simberbest.dcs.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.simberbest.dcs.dao.DcsInformationDao;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImpl;
import edu.simberbest.dcs.service.DcsInformationService;
import edu.simberbest.dcs.serviceImpl.DcsInformationServiceImpl;

/**
 * @author sbbpvi
 *
 */
@Configuration
public class AppConfig {

	
	@Bean
	    public DcsInformationService dataService() {
	        return new DcsInformationServiceImpl();
	    }
	@Bean
    public DcsInformationDao dataServiceDao() {
        return new DcsInformationDaoImpl();
    }
	
	
}
