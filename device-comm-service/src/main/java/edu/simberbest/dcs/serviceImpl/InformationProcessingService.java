package edu.simberbest.dcs.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.entity.IpVsMac;
import edu.simberbest.dcs.socketServer.ServerListener;

/**
 * @author sbbpvi
 *  to process the data for pi and caching the data
 *
 */
public class InformationProcessingService implements Runnable {
	private static final Logger Logger = LoggerFactory.getLogger(InformationProcessingService.class);
	public static volatile ConcurrentLinkedQueue<InformationPacket> infoQueForPi = new ConcurrentLinkedQueue<>();
	public static volatile ConcurrentHashMap<InformationPacket, InformationPacket> inmformationMap = new ConcurrentHashMap<>();

	@Override
	public void run() {
		Logger.info("Enter InformationProcessingService||Insertion data in queue");
		try {
			while (true) {
				if (!ServerListener.informationQueue.isEmpty()) {
					Iterator<Object> iter = ServerListener.informationQueue.iterator();
					while (iter.hasNext()) {
						Object obj = iter.next();
						if (obj instanceof InformationPacket) {
							InformationPacket infPct = (InformationPacket) obj;
							infoQueForPi.add(infPct);
							InformationPacket infoPckt = inmformationMap.get(infPct);
							if (infPct.getEnergy() != null && infoPckt != null) {
								infoPckt.setEnergy(infPct.getEnergy());
								infoPckt.setEnTimeStamp(infPct.getTimestamp());
							}
							if (infPct.getPower() != null && infoPckt != null) {
								infoPckt.setPower(infPct.getPower());
								infoPckt.setPwTimeStamp(infPct.getTimestamp());
							}
							if (infPct.getRelay() != null && infoPckt != null) {
								infoPckt.setRelay(infPct.getRelay());
								infoPckt.setRlyTimeStamp(infPct.getTimestamp());
							}
							if (infoPckt != null) {
								inmformationMap.put(infPct, infoPckt);
							} else {
								infPct.setEnTimeStamp(infPct.getTimestamp());
								infPct.setPwTimeStamp(infPct.getTimestamp());
								infPct.setRlyTimeStamp(infPct.getTimestamp());
								inmformationMap.put(infPct, infPct);
							}
							iter.remove();
						}
						if (obj instanceof IpVsMac) {
							IpVsMac ipVsMac = (IpVsMac) obj;
							List<InformationPacket> listOfIpMac = getAllIpMacs(obj);
							Set<InformationPacket> ipMac = inmformationMap.keySet();
							Iterator<InformationPacket> itr = ipMac.iterator();
							while (itr.hasNext()) {
								if (listOfIpMac.contains(itr.next())) {
									itr.remove();
								}
							}
							for (InformationPacket s : ipMac) {
								if (inmformationMap.contains(s)) {
									inmformationMap.remove(s);
								}
							}
							iter.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.error("Exception in processing Information Packet", e);
		}
		Logger.info("Exit InformationProcessingService||Insertion data in queue");
	}

private List<InformationPacket> getAllIpMacs(Object obj) {
		List<InformationPacket> ipList = new ArrayList<>();
		IpVsMac ipVsMac = (IpVsMac) obj;
		for (String s : ipVsMac.getMacIds()) {
			InformationPacket informationPacket = new InformationPacket(ipVsMac.getIpAddress(), s);
			ipList.add(informationPacket);
		}
		return ipList;
	}

}
