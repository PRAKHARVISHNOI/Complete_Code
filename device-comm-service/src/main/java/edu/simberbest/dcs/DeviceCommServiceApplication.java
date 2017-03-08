package edu.simberbest.dcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.serviceImpl.InfoInsertionInPiProcess;
import edu.simberbest.dcs.serviceImpl.InformationProcessingService;
import edu.simberbest.dcs.socketServer.ServerListener;

/**
 * @author sbbpvi
 *
 */
@SpringBootApplication
@ComponentScan(basePackages={"edu.simberbest.dcs.*"})
public class DeviceCommServiceApplication {

	/**
	 * @param args
	 * method to initiate application  
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext=	SpringApplication.run(DeviceCommServiceApplication.class, args);
		  ServerListener serverListener= (ServerListener)applicationContext.getBean("serverListener");
		 serverListener.startServer();
		 InformationProcessingService informationProcessingService= (InformationProcessingService)applicationContext.getBean("informationProcessingService");
		 new Thread( informationProcessingService).start();
		InfoInsertionInPiProcess infoInsertionInPiProcess= (InfoInsertionInPiProcess)applicationContext.getBean("infoInsertionInPiProcess");
		 new Thread(infoInsertionInPiProcess).start();
	}
	
	/**
	 * running separate thread for socket server, pi Thread and caching service
	 */
	/*static void runningThread(){
		CommunicationServiceConstants.loadProperties();
		new ServerListener().startServer();
		new Thread(new InformationProcessingService()).start();
		new Thread(new InfoInsertionInPiProcess()).start();
		
	}*/
	
}
