package edu.simberbest.dcs.serviceImpl;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.entity.InformationPacket;

/**
 * @author sbbpvi
 * thread to initiate insertion of data in pi
 *
 */
public class InfoInsertionInPiProcess implements Runnable {
	private static final Logger Logger = LoggerFactory.getLogger(InfoInsertionInPiProcess.class);
	@Override
	public void run() {
		Logger.info("Enter InfoInsertionInPiProcess||Insert data In Pi");
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(CommunicationServiceConstants.INFORMATION_SERVICE_THREAD_POOL_FOR_PI);
			while (true) {
				if (InformationProcessingService.infoQueForPi.size() > 0) {
					Iterator<InformationPacket> iter = InformationProcessingService.infoQueForPi.iterator();
					while (iter.hasNext()) {
						executorService.execute(new InfoInsertionInPiService(iter.next()));
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
