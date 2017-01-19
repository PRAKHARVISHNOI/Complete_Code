package edu.simberbest.dcs.serviceImpl;

import java.util.concurrent.ConcurrentHashMap;

import edu.simberbest.dcs.entity.InformationPacket;

/**
 * This class not been used, need to remove
 *
 * 
 */

@Deprecated
public class InformationCachingService implements Runnable {

	private InformationPacket informationPckt;
	public static volatile ConcurrentHashMap<InformationPacket, InformationPacket> inmformationMap = new ConcurrentHashMap<>();

	public InformationCachingService() {
		super();
	}

	public InformationCachingService(InformationPacket informationPckt) {
		super();
		this.informationPckt = informationPckt;
	}

	@Override
	public void run() {
		inmformationMap.put(informationPckt, informationPckt);
	}
}
