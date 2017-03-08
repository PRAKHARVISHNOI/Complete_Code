package edu.simberbest.dcs.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.exception.ApplicationException;
import edu.simberbest.dcs.service.DcsInformationService;

/**
 * @author sbbpvi
 * Creating task to insert data in pi
 */
@Deprecated
public class InfoInsertionInPiService implements Runnable {

	
	private static final Logger Logger = LoggerFactory.getLogger(InfoInsertionInPiService.class);
	
	private PlugLoadInformationPacket informationPacket;
	@Autowired
	DcsInformationServiceImpl dcsInformationService;

	/**
	 * @param informationPacket
	 */
	public InfoInsertionInPiService(PlugLoadInformationPacket informationPacket) {
		super();
		this.informationPacket = informationPacket;
	}
	public InfoInsertionInPiService() {
		super();
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Logger.info("Enter InfoInsertionInPiService||Insert current data to Pi");
		try {
			dcsInformationService.insertCurrentFeedToPie(informationPacket);
		} catch (ApplicationException e) {
	    Logger.error("Exception in Insertion to Pi database", e);
		}
		Logger.info("Exit InfoInsertionInPiService");
	}

}
