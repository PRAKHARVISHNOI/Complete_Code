package edu.simberbest.dcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.serviceImpl.InfoInsertionInPiProcess;
import edu.simberbest.dcs.serviceImpl.InformationProcessingService;
import edu.simberbest.dcs.socketServer.ServerListener;

/**
 * @author sbbpvi
 *
 */
@SpringBootApplication
public class DeviceCommServiceApplication {

	/**
	 * @param args
	 * method to initiate application
	 */
	public static void main(String[] args) {
		SpringApplication.run(DeviceCommServiceApplication.class, args);
		runningThread();
	}
	
	/**
	 * running separate thread for socket server, pi Thread and caching service
	 */
	static void runningThread(){
		CommunicationServiceConstants.loadProperties();
		new ServerListener().startServer();
		new Thread(new InformationProcessingService()).start();
		new Thread(new InfoInsertionInPiProcess()).start();
		
	}
	
}
