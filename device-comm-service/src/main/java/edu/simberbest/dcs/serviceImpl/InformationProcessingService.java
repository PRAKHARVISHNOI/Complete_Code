package edu.simberbest.dcs.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.entity.IpVsMac;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.socketServer.ServerListener;

/**
 * @author sbbpvi to process the data for pi and caching the data
 *
 */
public class InformationProcessingService implements Runnable {
	private static final Logger Logger = LoggerFactory.getLogger(InformationProcessingService.class);
	public static volatile ConcurrentLinkedQueue<PlugLoadInformationPacket> infoQueForPi = new ConcurrentLinkedQueue<>();
	public static volatile ConcurrentHashMap<PlugLoadInformationPacket, PlugLoadInformationPacket> CACHE = new ConcurrentHashMap<>();

	@Override
	public void run() {
		Logger.info("Enter InformationProcessingService||Insertion data in queue");
		CommunicationServiceConstants.loadProperties();
		try {
			while (true) {
				if (!ServerListener.informationQueue.isEmpty()) {

					// ServerListener.informationQueue.

					// Iterator<Object> iter =
					// ServerListener.informationQueue.iterator();
					// while (iter.hasNext()) {
					// Object obj = iter.next();
					Object obj = ServerListener.informationQueue.poll();
					if (obj != null) {
						if (obj instanceof PlugLoadInformationPacket) {
							PlugLoadInformationPacket localInfoPacket = (PlugLoadInformationPacket) obj;
							infoQueForPi.add(localInfoPacket);
							PlugLoadInformationPacket informationPacket = CACHE.get(localInfoPacket);
							if (informationPacket != null) {
								if (localInfoPacket.getEnergy() != null) {
									informationPacket.setEnergy(localInfoPacket.getEnergy());
									informationPacket.setEnTimeStamp(localInfoPacket.getTimestamp());
								}
								if (localInfoPacket.getPower() != null) {
									informationPacket.setPower(localInfoPacket.getPower());
									informationPacket.setPwTimeStamp(localInfoPacket.getTimestamp());
								}
								if (localInfoPacket.getRelay() != null) {
									informationPacket.setRelay(localInfoPacket.getRelay());
									informationPacket.setRlyTimeStamp(localInfoPacket.getTimestamp());
								}
								CACHE.put(localInfoPacket, informationPacket);
							} else {
								localInfoPacket.setEnTimeStamp(localInfoPacket.getTimestamp());
								localInfoPacket.setPwTimeStamp(localInfoPacket.getTimestamp());
								localInfoPacket.setRlyTimeStamp(localInfoPacket.getTimestamp());
								CACHE.put(localInfoPacket, localInfoPacket);
							}
							// iter.remove();
						}
						if (obj instanceof IpVsMac) {
							IpVsMac ipVsMac = (IpVsMac) obj;
							List<PlugLoadInformationPacket>listofPlugLoads=getAllIpMacs(ipVsMac);
						//	List<String> listOfIpMac = ipVsMac.getMacIds();
							Set<PlugLoadInformationPacket> informationPackets = CACHE.keySet();
							Iterator<PlugLoadInformationPacket> itr = informationPackets.iterator();
							while (itr.hasNext()) {
								PlugLoadInformationPacket cacheInformationPacket = itr.next();
								if (!listofPlugLoads.contains(cacheInformationPacket)) {
									CACHE.remove(cacheInformationPacket);
								}
							}
							// inmformationMap.keySet().removeAll(informationPackets);
							/*
							 * for (InformationPacket s : ipMac) { if
							 * (inmformationMap.contains(s)) {
							 * inmformationMap.remove(s); } }
							 */
							// iter.remove();
							// }
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.error("Exception in processing Information Packet", e);
		}
		Logger.info("Exit InformationProcessingService||Insertion data in queue");
	}

	
	private List<PlugLoadInformationPacket> getAllIpMacs(Object obj) {
		List<PlugLoadInformationPacket> ipList = new ArrayList<>();
		IpVsMac ipVsMac = (IpVsMac) obj;
		for (String s : ipVsMac.getMacIds()) {
			PlugLoadInformationPacket informationPacket = new PlugLoadInformationPacket(ipVsMac.getIpAddress(), s);
			ipList.add(informationPacket);
		}
		return ipList;
	}
	 

}
