package edu.simberbest.dcs.socketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.entity.IpVsMac;
import edu.simberbest.dcs.socketClient.InstructionClient;

/**
 * @author sbbpvi
 * 
 * socket server to receive data from raspberry pi 
 *
 */
public class ServerListener {
	private static final Logger Logger = LoggerFactory.getLogger(ServerListener.class);
	public static volatile ConcurrentLinkedQueue<Object> informationQueue = new ConcurrentLinkedQueue<>();

	public void startServer() {
		Logger.info("Enter ServerListener||Running Socket Server");
		final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(20);

		Runnable serverTask = () -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8000);
				System.out.println("Waiting for clients to connect...");
				while (true) {
					Socket clientSocket = serverSocket.accept();

					Runnable gettingClient = () -> {
						connectToClient(clientSocket);
					};

					clientProcessingPool.submit(gettingClient);
				}
			} catch (IOException e) {
				Logger.error("Unable to process client request",e);
				e.printStackTrace();
			}
		};
		Thread serverThread = new Thread(serverTask);
		serverThread.start();
	}

	void connectToClient(Socket clientSocket) {

		System.out.println("Got a client !");
		try {
			/* Get Data From Client */
			int red = -1;
			byte[] buffer = new byte[5 * 1024]; // a read buffer of 5KiB
			byte[] redData;
			StringBuilder clientData = new StringBuilder();
			String redDataText;
			while ((red = clientSocket.getInputStream().read(buffer)) > -1) {
				redData = new byte[red];
				System.arraycopy(buffer, 0, redData, 0, red);
				redDataText = new String(redData, "UTF-8"); // assumption
															// that client
															// sends data
															// UTF-8 encoded
				System.out.println("message part recieved:" + redDataText);
				clientData.append(redDataText);
			}
			System.out.println("Data From Client :" + clientData.toString());

			String str = clientData.toString();
			/*
			 * 1) For sending info to the main application (plugload data
			 * update) Raspberry/IP_address/Plugwise/Mac_Id/Timestamp/
			 * TimestampValue/Power/PowerValue/Energy/EnergyValue/Relay/
			 * RelayValue 2) For sending node list for particular raspberry
			 * Raspberry/IP_address/Plugwise/Mac_Id1/ Mac_Id2/ Mac_Id3/
			 * Mac_Id4…..
			 */

			if (str.contains("Timestamp")) {
				String[] infoPckts = str.split("\\/");
				InformationPacket infoPckt = new InformationPacket();
				for (int i = 0; i < infoPckts.length; i++) {

					if (infoPckts[i].equals("Raspberry")) {
						infoPckt.setIpAddress(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals("Plugwise")) {
						infoPckt.setMacId(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals("Timestamp")) {
						infoPckt.setTimestamp(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals("Power")) {
						infoPckt.setPower(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals("Energy")) {
						infoPckt.setEnergy(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals("Relay")) {
						infoPckt.setRelay(infoPckts[i + 1]);
					}
				}
				if (infoPckt.getMacId() != null) {
					informationQueue.add(infoPckt);
				}
			} else {
				String[] infoPckts = str.split("\\/");
				IpVsMac infoPckt = new IpVsMac();
				for (int i = 0; i < infoPckts.length; i++) {

					if (infoPckts[i].equals("Raspberry")) {
						infoPckt.setIpAddress(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals("Plugwise")) {
						List<String> macIds = new LinkedList<>();
						for (int j = i + 1; j < infoPckts.length; j++) {
							macIds.add(infoPckts[j]);
							i++;
						}
						infoPckt.setMacIds(macIds);
					}
				}
				if (infoPckt.getIpAddress() != null) {
					HashMap<String, List<String>> ipAddress = new HashMap<>();
					ipAddress.put(infoPckt.getIpAddress(), infoPckt.getMacIds());
					infoPckt.setMapOfMacs(ipAddress);
					informationQueue.add(infoPckt);
				}
			}

			clientSocket.close();
		} catch (IOException e) {
			Logger.error("Exception in listining client", e);
		}

	}

}