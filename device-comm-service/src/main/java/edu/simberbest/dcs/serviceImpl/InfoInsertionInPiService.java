package edu.simberbest.dcs.serviceImpl;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.exception.ApplicationException;

public class InfoInsertionInPiService implements Runnable {

	private InformationPacket informationPacket;

	public InfoInsertionInPiService(InformationPacket informationPacket) {
		super();
		this.informationPacket = informationPacket;
	}

	public InfoInsertionInPiService() {
		super();
	}

	@Override
	public void run() {
		try {
			new DcsInformationServiceImpl().insertCurrentFeedToPie(informationPacket);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
