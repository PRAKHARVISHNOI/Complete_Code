package edu.simberbest.dcs.socketClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.serviceImpl.InformationProcessingService;


/**
 * @author sbbpvi
 * 
 * Socket client to send instruction to raspberry pi
 *
 */
public class InstructionClient {

	private static final Logger Logger = LoggerFactory.getLogger(InstructionClient.class);
	public String socketConnection(PlugLoadInstructionPacket insPackt) {
		Logger.info("Enter InstructionClient||Client connection");
		   String data = insPackt.toString();
		   String mac= insPackt.getMacId();
		   String Ip = null;
		   String message=null;
		   //Getting Ip from CACHE for specific Mac
		   for(PlugLoadInformationPacket informationPacket : InformationProcessingService.CACHE.keySet()){
			   if(informationPacket.getMacId().equals(mac)){
				  Ip= informationPacket.getIpAddress(); 
			   }
		   }
		   
	      try {
	    	  // Sending data to RP Server
	         Socket skt = new Socket(Ip, CommunicationServiceConstants.CLIENT_PORT);
	         BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
	         System.out.print("Received string: '");

	         PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
	         System.out.print("Sending string: '" + data + "'\n");
	         out.print(data); 
	      
	        // Receiving Message
	        
	         while (!in.ready()) {}
	         System.out.println(); // Read one line and output it 
	         message=in.readLine();
	         System.out.print("'\n");
	         in.close();
	         out.close();
	         skt.close();
	      }
	      catch(Exception e) {
	    	  Logger.error("Exception in sending Instruction packet", e);
	      }
		return message;
	   }
	   
}
