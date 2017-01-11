package edu.simberbest.dcs.serviceImpl;

import java.util.concurrent.ConcurrentHashMap;

import edu.simberbest.dcs.entity.InformationPacket;

public class InformationCashingService implements Runnable {

	private InformationPacket informationPckt;
	public static volatile ConcurrentHashMap<String, InformationPacket> inmformationMap = new ConcurrentHashMap<>();

	public InformationCashingService() {
		super();
	}

	public InformationCashingService(InformationPacket informationPckt) {
		super();
		this.informationPckt = informationPckt;
	}

	@Override
	public void run() {
		inmformationMap.put(informationPckt.getMacId()+"##"+informationPckt.getIpAddress(), informationPckt);
	}
}
