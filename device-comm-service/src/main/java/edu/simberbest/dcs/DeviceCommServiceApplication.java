package edu.simberbest.dcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.simberbest.dcs.serviceImpl.InfoInsertionInPiProcess;
import edu.simberbest.dcs.serviceImpl.InformationProcessingService;
import edu.simberbest.dcs.socketServer.ServerListener;

@SpringBootApplication
public class DeviceCommServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceCommServiceApplication.class, args);
		runningThread();
	}
	
	static void runningThread(){
		new Thread(new InformationProcessingService()).start();
		new Thread(new InfoInsertionInPiProcess()).start();
		new ServerListener().startServer();
	}
	
}
