package edu.simberbest.dcs.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.entity.Response;
import edu.simberbest.dcs.socketClient.InstructionClient;
@Deprecated
public class DcsMultipleInstructionService implements Callable<List<Response>> {

	private List<PlugLoadInstructionPacket> plugLoadForIp;
	@Autowired
	InstructionClient instructionClient;
	@Value("${PLUGLOAD_ON}")
	public String PLUGLOAD_ON;
	// PLUGLOAD_OFF
	@Value("${PLUGLOAD_OFF}")
	public String PLUGLOAD_OFF;
	// PLUGLOAD_OFFLINE
	@Value("${PLUGLOAD_OFFLINE}")
	public String PLUGLOAD_OFFLINE;
	//COMMAND_EXECUTED
	@Value("${COMMAND_EXECUTED}")
	public String COMMAND_EXECUTED;
	
	public DcsMultipleInstructionService(List<PlugLoadInstructionPacket> plugLoadForIp) {
		super();
		this.plugLoadForIp = plugLoadForIp;
	}

	@Override
	public List<Response> call() {
		try{
		String message;
		List<Response> responses=new ArrayList<>();
		for(PlugLoadInstructionPacket instructionPacket : plugLoadForIp){
			Response response = new Response();
			message = instructionClient.socketConnection(instructionPacket);
            response.setState(message);
            response.setMacID(instructionPacket.getMacId());
            response.setMessageCode(COMMAND_EXECUTED);
            if (message.trim().equals("0")) {
            	message = PLUGLOAD_OFF;
    		}
    		if (message.trim().equals("1")) {
    			message = PLUGLOAD_ON;
    		}
    		if (message.trim().equals("2")) {
    			message = PLUGLOAD_OFFLINE;
    		}
            response.setMessageDetails(message);
            responses.add(response);
            return responses;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

}
