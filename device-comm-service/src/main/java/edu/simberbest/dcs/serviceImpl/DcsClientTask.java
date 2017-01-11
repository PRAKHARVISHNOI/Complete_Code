package edu.simberbest.dcs.serviceImpl;

import edu.simberbest.dcs.entity.InstructionPacket;
import edu.simberbest.dcs.socketClient.InstructionClient;

public class DcsClientTask implements Runnable{



	private InstructionPacket insPckt;
	
	public DcsClientTask(InstructionPacket insPckt) {
		super();
		this.insPckt = insPckt;
	}

	@Override
	public void run() {
    InstructionPacket insPackt= insPckt;
    // sendng packet to raspberry pi
    InstructionClient insServer= new InstructionClient();
    insServer.socketConnection(insPackt);
	}

}
