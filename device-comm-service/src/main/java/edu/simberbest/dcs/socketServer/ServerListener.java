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

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
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
		//CommunicationServiceConstants.loadProperties();
		Logger.info("Enter ServerListener||Running Socket Server");
		final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(CommunicationServiceConstants.SOCKET_SERVER_POOL);

		Runnable serverTask = () -> {
			try {
				ServerSocket serverSocket = new ServerSocket(CommunicationServiceConstants.SERVER_PORT);
			//	System.out.println("Waiting for clients to connect...");
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

	//	System.out.println("Got a client !");
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
				redDataText = new String(redData, CommunicationServiceConstants.UTF); // assumption
															// that client
															// sends data
															// UTF-8 encoded
				//System.out.println("message part recieved:" + redDataText);
				clientData.append(redDataText);
			}
			//System.out.println("Data From Client :" + clientData.toString());

			String str = clientData.toString();
			// Segregating data according to packet type

			if (str.contains(CommunicationServiceConstants.TIMESTAMP)) {
				String[] infoPckts = str.split("\\/");
				PlugLoadInformationPacket infoPckt = new PlugLoadInformationPacket();
				for (int i = 0; i < infoPckts.length; i++) {

					if (infoPckts[i].equals(CommunicationServiceConstants.RESPBERRY)) {
						infoPckt.setIpAddress(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals(CommunicationServiceConstants.PLUGWISE)) {
						infoPckt.setMacId(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals(CommunicationServiceConstants.TIMESTAMP)) {
						infoPckt.setTimestamp(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals(CommunicationServiceConstants.POWER)) {
						infoPckt.setPower(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals(CommunicationServiceConstants.ENERGY)) {
						infoPckt.setEnergy(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals(CommunicationServiceConstants.RELAY)) {
						infoPckt.setRelay(infoPckts[i + 1]);
					}
				}
				if (infoPckt.getMacId() != null) {
					informationQueue.add(infoPckt);
					//System.out.println(informationQueue);
				}
			} else {
				String[] infoPckts = str.split("\\/");
				IpVsMac infoPckt = new IpVsMac();
				for (int i = 0; i < infoPckts.length; i++) {

					if (infoPckts[i].equals(CommunicationServiceConstants.RESPBERRY)) {
						infoPckt.setIpAddress(infoPckts[i + 1]);
					}
					if (infoPckts[i].equals(CommunicationServiceConstants.PLUGWISE)) {
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