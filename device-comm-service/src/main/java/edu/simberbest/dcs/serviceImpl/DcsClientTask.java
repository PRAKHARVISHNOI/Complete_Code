package edu.simberbest.dcs.serviceImpl;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.simberbest.dcs.controller.DcsController;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.socketClient.InstructionClient;

/**
 * @author sbbpvi
 *
 * Task to send instruction packet to socket client and get message from raspberry pi server 
 */
public class DcsClientTask implements 	Callable<String>{
	private static final Logger Logger = LoggerFactory.getLogger(DcsClientTask.class);
	private PlugLoadInstructionPacket instructionPacket;
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
	    InstructionClient instructionClient= new InstructionClient();
	    message= instructionClient.socketConnection(packet);
	    Logger.info("Exit DcsClientTask");
		return  message;
	}
}
