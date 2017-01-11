package edu.simberbest.dcs.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.simberbest.dcs.constants.CommuniationServiceConstants;
import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.entity.IpVsMac;
import edu.simberbest.dcs.exception.ApplicationException;
import edu.simberbest.dcs.socketServer.ServerListener;

public class InformationProcessingService implements Runnable {

	
	public static volatile ConcurrentLinkedQueue<InformationPacket > infoQueForPi= new ConcurrentLinkedQueue<>();
	public static volatile ConcurrentHashMap<String, InformationPacket> inmformationMap = new ConcurrentHashMap<>();
	@Override
	public void run() {
		try{
		ExecutorService executorService= Executors.newFixedThreadPool(CommuniationServiceConstants.INFORMATION_SERVICE_THREAD_POOL);
		 while(true){
				if(ServerListener.inmformationQueue.size()>0){
					Iterator<Object> iter = ServerListener.inmformationQueue.iterator();
					while (iter.hasNext()) {
						Object obj=iter.next();
						if(obj instanceof InformationPacket ) {
							
						InformationPacket infPct = (InformationPacket) obj;
						// using only one thread for adding information
						infoQueForPi.add(infPct);
						inmformationMap.put(infPct.getIpAddress()+"##"+infPct.getMacId(), infPct);
					//	executorService.execute(new InformationCashingService(infPct));
					    iter.remove();
						}
						if(obj instanceof IpVsMac ) {
							IpVsMac ipVsMac =(IpVsMac)obj; 
						 List<String>listOfIpMac=getAllIpMacs(obj);
						 Set<String>ipMac=inmformationMap.keySet();
						 Iterator<String> itr = ipMac.iterator();
							while (itr.hasNext()) {
								if(listOfIpMac.contains(itr.next())){
									itr.remove();
								}
							}
						 for(String s :  ipMac){
							 if(inmformationMap.contains(s)){
								 inmformationMap.remove(s);
							 }
						  }
						 iter.remove();
						}
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	private List<String> getAllIpMacs(Object obj) {
		// TODO Auto-generated method stub
		List<String>ipList= new ArrayList<>();
		IpVsMac ipVsMac= (IpVsMac)obj;
		for(String s : ipVsMac.getMacIds() ){
			ipList.add(ipVsMac.getIpAddress()+"##"+s);
		}
		return ipList;
	}

}
