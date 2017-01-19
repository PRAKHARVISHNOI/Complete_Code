package edu.simberbest.dcs.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.exception.ApplicationException;

/**
 * @author sbbpvi
 * Creating task to insert data in pi
 */
public class InfoInsertionInPiService implements Runnable {

	private static final Logger Logger = LoggerFactory.getLogger(InfoInsertionInPiService.class);
	
	private InformationPacket informationPacket;

	/**
	 * @param informationPacket
	 */
	public InfoInsertionInPiService(InformationPacket informationPacket) {
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
			new DcsInformationServiceImpl().insertCurrentFeedToPie(informationPacket);
		} catch (ApplicationException e) {
			Logger.error("Exception in Insertion to Pi database", e);
		}
		Logger.info("Exit InfoInsertionInPiService");
	}

}
