package edu.simberbest.dcs.socketClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import edu.simberbest.dcs.entity.InstructionPacket;
import edu.simberbest.dcs.serviceImpl.InformationProcessingService;


public class InstructionClient {


	public void socketConnection(InstructionPacket insPackt) {
		   String data = insPackt.toString();
		   String mac= insPackt.getMacId();
		   String Ip = null;
		   for(String s : InformationProcessingService.inmformationMap.keySet()){
			   if(s.contains(mac)){
				  Ip= s.split("##")[0]; 
			   }
		   }
		   
	      try {
	         Socket skt = new Socket(Ip, 8001);
	         BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
	         System.out.print("Received string: '");

	         PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
	         System.out.print("Sending string: '" + data + "'\n");
	         out.print(data);
	         out.close();
	         skt.close();
	        
	         while (!in.ready()) {}
	         System.out.println(in.readLine()); // Read one line and output it

	         System.out.print("'\n");
	         in.close();
	      }
	      catch(Exception e) {
	         System.out.print("Whoops! It didn't work!\n"+e.getMessage());
	      }
	   }
	   
}
