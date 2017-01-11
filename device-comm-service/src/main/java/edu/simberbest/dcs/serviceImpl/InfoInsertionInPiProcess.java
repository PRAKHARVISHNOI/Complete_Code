package edu.simberbest.dcs.serviceImpl;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.socketServer.ServerListener;

public class InfoInsertionInPiProcess implements Runnable {

	
	@Override
	public void run() {
		try{
		ExecutorService executorService= Executors.newFixedThreadPool(3);
		while(true){
			if(InformationProcessingService.infoQueForPi.size()>0){
				Iterator<InformationPacket> iter = InformationProcessingService.infoQueForPi.iterator();
				while (iter.hasNext()) {
					executorService.execute(new InfoInsertionInPiService(iter.next()));
				    iter.remove();
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
