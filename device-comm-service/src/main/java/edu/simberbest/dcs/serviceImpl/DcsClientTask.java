package edu.simberbest.dcs.serviceImpl;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.simberbest.dcs.controller.DcsController;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.service.DcsInformationService;
import edu.simberbest.dcs.socketClient.InstructionClient;

/**
 * @author sbbpvi
 *
 * Task to send instruction packet to socket client and get message from raspberry pi server 
 */
@Deprecated
public class DcsClientTask implements 	Callable<String>{
	private static final Logger Logger = LoggerFactory.getLogger(DcsClientTask.class);
	private PlugLoadInstructionPacket instructionPacket;
	@Autowired
	InstructionClient instructionClient;
	/**
	 * @param insPckt
	 */
	public DcsClientTask(PlugLoadInstructionPacket instructionPacket) {
		super();
		this.instructionPacket = instructionPacket;
	}
	
	
	
	
	@Override
	public String call() throws Exception {
		Logger.info("Enter DcsClientTask Connecting Socket");
		PlugLoadInstructionPacket packet= instructionPacket;
		String message="";
	    //InstructionClient instructionClient= new InstructionClient();
	    message= instructionClient.socketConnection(packet);
	    Logger.info("Exit DcsClientTask");
		return  message;
	}




	public Callable<String> submitTask(PlugLoadInstructionPacket instructionPacket2) {
		this.instructionPacket = instructionPacket;
		
		
		
		
		return null;
	}
}
