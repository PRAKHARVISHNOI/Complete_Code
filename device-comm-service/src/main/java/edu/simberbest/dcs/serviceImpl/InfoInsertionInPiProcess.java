package edu.simberbest.dcs.serviceImpl;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.exception.ApplicationException;

/**
 * @author sbbpvi
 * thread to initiate insertion of data in pi
 *
 */
public class InfoInsertionInPiProcess implements Runnable {
	private static final Logger Logger = LoggerFactory.getLogger(InfoInsertionInPiProcess.class);
	
	@Autowired
	DcsInformationServiceImpl dcsInformationService;

	@Value("${INFORMATION_SERVICE_THREAD_POOL_FOR_PI}")
	public Integer INFORMATION_SERVICE_THREAD_POOL_FOR_PI;
	@Override
	public void run() {
		Logger.info("Enter InfoInsertionInPiProcess||Insert data In Pi");
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(INFORMATION_SERVICE_THREAD_POOL_FOR_PI);
		// Loop to check the Queue continuously 
			while (true) {
				if (InformationProcessingService.infoQueForPi.size() > 0) {
					
					Iterator<PlugLoadInformationPacket> iter = InformationProcessingService.infoQueForPi.iterator();
					//PlugLoadInformationPacket informationPacket=new PlugLoadInformationPacket();
					while (iter.hasNext()) {
						// need change
						PlugLoadInformationPacket informationPacket=iter.next();
						 Runnable task = () -> {
		                    	try {
		                			dcsInformationService.insertCurrentFeedToPie(informationPacket);
		                		} catch (Exception e) {
		                	    Logger.error("Exception in Insertion to Pi database", e);
		                		}
							};
						executorService.execute(task);
						iter.remove();
					}
                   
				}
			}
			
		} catch (Exception e) {
			Logger.error("Exception in Running Pi Thread", e);
		}
		Logger.info("Exit InfoInsertionInPiProcess Task");
	}

}
