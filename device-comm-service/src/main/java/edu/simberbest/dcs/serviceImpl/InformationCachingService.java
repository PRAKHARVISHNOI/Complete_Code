package edu.simberbest.dcs.serviceImpl;

import java.util.concurrent.ConcurrentHashMap;

import edu.simberbest.dcs.entity.PlugLoadInformationPacket;

/**
 * This class not been used, need to remove
 *
 * 
 */

@Deprecated
public class InformationCachingService implements Runnable {

	private PlugLoadInformationPacket informationPckt;
	public static volatile ConcurrentHashMap<PlugLoadInformationPacket, PlugLoadInformationPacket> inmformationMap = new ConcurrentHashMap<>();

	public InformationCachingService() {
		super();
	}

	public InformationCachingService(PlugLoadInformationPacket informationPckt) {
		super();
		this.informationPckt = informationPckt;
	}

	@Override
	public void run() {
		inmformationMap.put(informationPckt, informationPckt);
	}
}
