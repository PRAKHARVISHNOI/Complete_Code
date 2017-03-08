package edu.simberbest.dcs.socketClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.serviceImpl.InformationProcessingService;

/**
 * @author sbbpvi
 * 
 *         Socket client to send instruction to raspberry pi
 *
 */
public class InstructionClient {

	private static final Logger Logger = LoggerFactory.getLogger(InstructionClient.class);
	@Value("${CLIENT_PORT}")
	public Integer CLIENT_PORT;
	@Value("${PLUGLOAD_ON}")
	public  String PLUGLOAD_ON;
	// PLUGLOAD_OFF
	@Value("${PLUGLOAD_OFF}")
	public  String PLUGLOAD_OFF;
	// PLUGLOAD_OFFLINE
	@Value("${PLUGLOAD_OFFLINE}")
	public  String PLUGLOAD_OFFLINE;
	@Value("${IP_NOT_PRESENT}")
	public  String IP_NOT_PRESENT;
/*	public String socketConnection1(PlugLoadInstructionPacket insPackt) {
		Logger.info("Enter InstructionClient||Client connection");
		String data = insPackt.toString();
		String mac = insPackt.getMacId();
		String Ip = null;
		String message = null;
		// Getting Ip from CACHE for specific Mac
		for (PlugLoadInformationPacket informationPacket : InformationProcessingService.CACHE.keySet()) {
			if (informationPacket.getMacId().equals(mac)) {
				Ip = informationPacket.getIpAddress();
			}
		}

		try {
			// while(true){
			// Sending data to RP Server
			System.out.println(
					Ip + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			Socket skt = new Socket(Ip, CommunicationServiceConstants.CLIENT_PORT);
			// if (!data.equals("")) {
			PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
			System.out.print("Sending string: '" + data + "'\n");
			out.print(data);
			// data = "";
			out.close();

			// }

			// Receiving Message
			BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
			
			 * BufferedReader stdIn = new BufferedReader( new
			 * InputStreamReader(System.in));
			 
			// message = getDataFromPort(Ip,
			// CommunicationServiceConstants.CLIENT_PORT);
			
			 * BufferedReader in = new BufferedReader(new
			 * InputStreamReader(skt.getInputStream()));
			 * System.out.print("Received string: '"); while (!in.ready()) {}
			 * System.out.println(); // Read one line and output it
			 * message=in.readLine();
			 * System.out.print("?????????????????????????????????????????"+
			 * message+"'\n"); in.close();
			 
			System.out.print("?????????????????????????????????????????" + "echo: " + in.readLine() + "'\n");

			skt.close();
			// }
		} catch (Exception e) {
			Logger.error("Exception in sending Instruction packet", e);
		}
		return message;
	}

	private String getDataFromPort(String ip, Integer CLIENT_PORT) throws IOException {

		Socket skt = new Socket(ip, CLIENT_PORT);
		BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		String message;
		System.out.print("Received string: in socket'");
		while (!in.ready()) {
		}
		message = in.readLine();
		in.close();
		skt.close();
		return message;
	}*/


	public String socketConnection(PlugLoadInstructionPacket insPackt) {
		Logger.info("Enter InstructionClient||Client connection");
		String data = insPackt.toString();
		String mac = insPackt.getMacId();
		String Ip = null;
		String message = null;
		// Getting Ip from CACHE for specific Mac
		for (PlugLoadInformationPacket informationPacket : InformationProcessingService.CACHE.keySet()) {
			if (informationPacket.getMacId().equals(mac)) {
				Ip = informationPacket.getIpAddress();
			}
		}
		if(Ip==null){
			message=IP_NOT_PRESENT;
			return message;
		}
		try {
			Socket socket = openSocket(Ip, CLIENT_PORT);

			String writeToAndReadFromSocket = writeToAndReadFromSocket(socket, data);

			message=getRelayStatus(writeToAndReadFromSocket);
			
			socket.close();
		} catch (Exception e) {
			Logger.error("Exception in sending Instruction packet", e);
		}
		return message;
	}

	/**
	 * @param writeToAndReadFromSocket
	 *  Method to get relay status ALL
	 */
	private String getRelayStatus(String writeToAndReadFromSocket) {
		String[] elements = writeToAndReadFromSocket.split("\\/");
		String relayStatus = null;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].equals(CommunicationServiceConstants.RELAY)) {
				relayStatus=elements[i + 1];
			}
		}
		
		if (relayStatus.trim().equals("0")) {
			relayStatus=	PLUGLOAD_OFF;
		}
		if (relayStatus.trim().equals("1")) {
			relayStatus=	PLUGLOAD_ON;
		}
		if (relayStatus.trim().equals("2")) {
			relayStatus=	PLUGLOAD_OFFLINE;
		}
		return relayStatus;
}

	private String writeToAndReadFromSocket(Socket socket, String writeTo) throws Exception {
		try {
			// write text to the socket
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write(writeTo);
			bufferedWriter.flush();

			// read text from the socket
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String str;
			while (!(str = bufferedReader.readLine()).equals("end")) {
				sb.append(str + "\n");
			}

			// close the reader, and return the results as a String
			bufferedReader.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Open a socket connection to the given server on the given port. This
	 * method currently sets the socket timeout value to 10 seconds. (A second
	 * version of this method could allow the user to specify this timeout.)
	 */
	private Socket openSocket(String server, int port) throws Exception {
		Socket socket = null;
		// open a socket and connect with a timeout limit
		try {
			InetAddress addr = InetAddress.getByName(server);
			SocketAddress sockaddr = new InetSocketAddress(addr, port);
			socket = new Socket();

			// this method will block for the defined number of milliseconds
			int timeout = 20000;
			socket.connect(sockaddr, timeout);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
}
