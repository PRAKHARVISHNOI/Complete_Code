package edu.simberbest.dcs.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.simberbest.dcs.Util.Util;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.dao.DcsInformationDao;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImplForPi;
import edu.simberbest.dcs.entity.MacStatus;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.entity.Response;
import edu.simberbest.dcs.exception.ApplicationException;
import edu.simberbest.dcs.exception.DaoException;
import edu.simberbest.dcs.service.DcsInformationService;
import edu.simberbest.dcs.socketClient.InstructionClient;

/**
 * @author sbbpvi used for sending information to pi, sending instruction to
 *         socket client and getting information from cache
 */
public class DcsInformationServiceImpl implements DcsInformationService {
	private static final Logger Logger = LoggerFactory.getLogger(DcsInformationServiceImpl.class);
	@Autowired
	InstructionClient instructionClient;
	@Autowired
	private DcsInformationDao dataServiceDao;
	@Autowired
	private DcsInformationDaoImplForPi dcsInformationDaoImplForPi;
	@Autowired
	DcsClientTask dcsClientTask;
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
	

	@Value("${INSTRUCTION_SERVICE_THREAD_POOL}")
	public Integer INSTRUCTION_SERVICE_THREAD_POOL;
	@Value("${CONNECTION_FAILURE}")
	public String CONNECTION_FAILURE;
	@Value("${ERROR_IN_RETRIVAL}")
	public String ERROR_IN_RETRIVAL;
	@Value("${CONNECTION_TIMEOUT}")
	public Integer CONNECTION_TIMEOUT;
	@Value("${CONNECTION_TIMEOUT_MESSAGE}")
	public String CONNECTION_TIMEOUT_MESSAGE;
	
	
	public static volatile ConcurrentLinkedQueue<PlugLoadInstructionPacket> instructionQueue = new ConcurrentLinkedQueue<>();

	/**
	 * method to call pi data-feed (non-Javadoc)
	 * 
	 * @see edu.simberbest.dcs.service.DcsInformationService#insertCurrentFeedToPie(edu.simberbest.dcs.entity.PlugLoadInformationPacket)
	 */
	@Override
	public boolean insertCurrentFeedToPie(PlugLoadInformationPacket infoPcket) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method insertCurrentFeedToPie: Param # " + infoPcket);
		try {
			dataServiceDao.insertCurrentFeedToTextFile(infoPcket);
			dcsInformationDaoImplForPi.insertCurrentFeedToPi(infoPcket);
		} catch (DaoException e) {
			e.printStackTrace();
			Logger.error("Exception While Data Entering In Pi" + e);
			throw new ApplicationException(e.getMessage());
		}
		Logger.info("Exit DcsInformationServiceImpl method insertCurrentFeedToPie");
		return false;
	}

	/**
	 * 
	 * method to process instruction packet (non-Javadoc)
	 * 
	 * @see edu.simberbest.dcs.service.DcsInformationService#processInstruction(edu.simberbest.dcs.entity.PlugLoadInstructionPacket)
	 */
	@Override
	public Response processInstruction(PlugLoadInstructionPacket instructionPacket) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method processInstruction: Param ## " + instructionPacket+"***********************"+System.currentTimeMillis());
		String flag = "";
		try {
			// Async Call for Instruction
			Future<String> future = null;
			ExecutorService executorService = Executors.newFixedThreadPool(INSTRUCTION_SERVICE_THREAD_POOL);
			Callable<String> task = () -> {
				String message = "";
				try {
					message = instructionClient.socketConnection(instructionPacket);
				} catch (Exception e) {
					Logger.error("Exception in Insertion to Pi database", e);
				}
				return message;
			};
			future = executorService.submit(task);
			// instructionQueue.remove(instructionPacket);
			flag = future.get(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
			// flag = future.get();
			
		} catch (TimeoutException e) {
			Logger.error("TimeOut While Sending Instruction" + e);
			Logger.info("Exit DcsInformationServiceImpl method processInstruction***********************"+System.currentTimeMillis());
			throw new ApplicationException(CONNECTION_TIMEOUT_MESSAGE, e);
		}
		catch (Exception e) {
			Logger.error("Exception While Sending Instruction" + e);
			Logger.info("Exit DcsInformationServiceImpl method processInstruction***********************"+System.currentTimeMillis());
			throw new ApplicationException(CONNECTION_FAILURE, e);
		}
		Logger.info("Exit DcsInformationServiceImpl method processInstruction***********************"+System.currentTimeMillis());
		String messageDetails="";
		String messageCode="";
		if (flag.trim().equals("0")) {
			messageDetails = PLUGLOAD_OFF;
			messageCode=COMMAND_EXECUTED;
		}
		else if (flag.trim().equals("1")) {
			messageDetails = PLUGLOAD_ON;
			messageCode=COMMAND_EXECUTED;
		}
		else if (flag.trim().equals("2")) {
			messageDetails = PLUGLOAD_OFFLINE;
			messageCode=COMMAND_EXECUTED;
		}
		
		Response response;
		if(!messageCode.equals("")){
		 response = new Response(flag,messageCode , messageDetails, instructionPacket.getMacId());
		}else{
			 response = new Response("",flag , messageDetails, instructionPacket.getMacId());
		}
		return response;

	}

	/**
	 * Method to get information from cache(Map) (non-Javadoc)
	 * 
	 * @see edu.simberbest.dcs.service.DcsInformationService#getDetails(java.lang.String)
	 */
	@Override
	public Collection<Object> getDetails(String mcId) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method getDetails: Param # " + mcId);
		Collection<Object> infoList = new LinkedList<>();
		try {
			// Sending Collection According to Mac As All or Mac ID
			if (mcId.equals(CommunicationServiceConstants.ALL)) {
				for (PlugLoadInformationPacket informationPacket : InformationProcessingService.CACHE.keySet()) {
					infoList.add(InformationProcessingService.CACHE.get(informationPacket));
				}
			} else {
				infoList = InformationProcessingService.CACHE.keySet().stream()
						.filter(it -> it.getMacId().contains(mcId)).collect(Collectors.toSet());
			}
			Logger.info("Exit DcsInformationServiceImpl method getDetails");
		} catch (Exception e) {
			Logger.error("Exception While Fetching Data From Cache" + e);
			throw new ApplicationException(ERROR_IN_RETRIVAL, e);
		}
		return infoList;
	}

	/*
	 * Method to get status from cache(Map) (non-Javadoc)
	 * 
	 * @see
	 * edu.simberbest.dcs.service.DcsInformationService#getStatus(java.lang.
	 * String)
	 */
	@Override
	public Collection<Object> getStatus(String macId) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method getStatus: Param # " + macId);
		Collection<Object> infoList = new LinkedList<>();
		List<PlugLoadInformationPacket> infoListTemp = new LinkedList();
		try {
			// Sending Collection According to Mac As All or Mac ID
			if (macId.equals(CommunicationServiceConstants.ALL)) {
				for (PlugLoadInformationPacket informationPacket : InformationProcessingService.CACHE.keySet()) {
					infoList.add(getStatusLabel(InformationProcessingService.CACHE.get(informationPacket).getRelay(),
							InformationProcessingService.CACHE.get(informationPacket).getMacId()));
				}
			} else {
				infoListTemp = InformationProcessingService.CACHE.keySet().stream()
						.filter(it -> it.getMacId().contains(macId)).collect(Collectors.toList());
				infoList.add(getStatusLabel(infoListTemp.get(0).getRelay(), infoListTemp.get(0).getMacId()));
			}
			Logger.info("Exit DcsInformationServiceImpl method getStatus");
		} catch (Exception e) {
			Logger.error("Exception While Fetching Data From Cache" + e);
			throw new ApplicationException(ERROR_IN_RETRIVAL, e);
		}
		return infoList;
	}

	/**
	 * @param relay
	 * @param macId
	 * @return method to create status locally
	 * @throws JSONException
	 * 
	 */
	private MacStatus getStatusLabel(String relay, String macId) throws JSONException {
		MacStatus getStatus = new MacStatus();
		if (relay.trim().equals("0")) {
			getStatus.setMacID(macId);
			getStatus.setStatus(CommunicationServiceConstants.OFF);
		}
		if (relay.trim().equals("1")) {
			getStatus.setMacID(macId);
			getStatus.setStatus(CommunicationServiceConstants.ON);
		}
		if (relay.trim().equals("2")) {
			getStatus.setMacID(macId);
			getStatus.setStatus(CommunicationServiceConstants.OFFLINE);
		}
		return getStatus;
	}

	/* (non-Javadoc)
	 * @see edu.simberbest.dcs.service.DcsInformationService#processMultipleInstruction(java.util.List) run() 
	 */
	@Override
	public List<Response> processMultipleInstruction(List<PlugLoadInstructionPacket> instructionPackets) throws ApplicationException {
		Logger.info("Enter processMultipleInstruction method processInstruction: Param # " + instructionPackets+"***********************"+System.currentTimeMillis());
		Map <String, ArrayList<PlugLoadInstructionPacket>>instructionMap = new HashMap<>();
		List<Response> finalResponseList= new ArrayList<>();
		Future<List<Response>> future = null;
		for(PlugLoadInstructionPacket instructionPacket : instructionPackets){
			String Ip= Util.getIp(instructionPacket.getMacId());
			if(instructionMap.get(Ip)==null){
				ArrayList<PlugLoadInstructionPacket>plugLoadForIp= new ArrayList<>();
				plugLoadForIp.add(instructionPacket);
			    instructionMap.put(Ip,plugLoadForIp);
			}
			else{
				ArrayList<PlugLoadInstructionPacket>plugLoadForIp=instructionMap.get(Ip);
				plugLoadForIp.add(instructionPacket);
				instructionMap.put(Ip,plugLoadForIp);
			}
		}
		ExecutorService executorService = Executors.newFixedThreadPool(INSTRUCTION_SERVICE_THREAD_POOL);
		
		List<PlugLoadInstructionPacket>plugInstructionPacket= new ArrayList<>();
		Set<String> ipSet=instructionMap.keySet();
		for (String ip : ipSet) {
			plugInstructionPacket = instructionMap.get(ip);
			List<Response> responseList = new ArrayList<>();
			// DcsMultipleInstructionService dcsMultipleInstructionService= new
			// DcsMultipleInstructionService(plugInstructionPacket);
			future=creatingThread(plugInstructionPacket,executorService,future);
			
			try {
				responseList = future.get(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				Logger.error("TimeOut While Sending Instruction" + e);
				Logger.info("Exit processMultipleInstruction method processInstruction***********************"+System.currentTimeMillis());
				responseList.add(new Response("", CONNECTION_FAILURE, "Connection not Stabilise for Ip: "+ip, "") );
			}
			catch (Exception e) {
				Logger.error("Exception While Sending Instruction" + e);
				Logger.info("Exit processMultipleInstruction method processInstruction***********************"+System.currentTimeMillis());
				responseList.add(new Response("", CONNECTION_FAILURE, "Connection not Stabilise for Ip: "+ip, "") );
			}
			finalResponseList.addAll(responseList);
		}
		return finalResponseList;
	}

	private Future<List<Response>> creatingThread(List<PlugLoadInstructionPacket> plugInstructionPacket, ExecutorService executorService, Future<List<Response>> future) {
		Callable<List<Response>> task = () -> {
			List<Response> responses = new ArrayList<>();
			for (PlugLoadInstructionPacket instructionPacket : plugInstructionPacket) {
				String messageDetails="";
				String messageCode="";
				String message="";
				message = instructionClient.socketConnection(instructionPacket);
				if (message.trim().equals("0")) {
					messageDetails = PLUGLOAD_OFF;
					messageCode=COMMAND_EXECUTED;
				}
				else if (message.trim().equals("1")) {
					messageDetails = PLUGLOAD_ON;
					messageCode=COMMAND_EXECUTED;
				}
				else if (message.trim().equals("2")) {
					messageDetails = PLUGLOAD_OFFLINE;
					messageCode=COMMAND_EXECUTED;
				}
				Response response;
				if(!messageCode.equals("")){
				 response = new Response(message,messageCode , messageDetails, instructionPacket.getMacId());
				}else{
					 response = new Response("",message , messageDetails, instructionPacket.getMacId());
				}
				responses.add(response);

			}
			return responses;
		};
		future = executorService.submit(task);
		return future;
	}
	
	@Override
	public List<Response> switchOnAllLoads() throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method switchOnAllLoads: Param "+System.currentTimeMillis());
		Map <String, ArrayList<PlugLoadInstructionPacket>>instructionMap = new HashMap<>();
		List<Response> finalResponseList= new ArrayList<>();
		Set <PlugLoadInformationPacket> informationSet=InformationProcessingService.CACHE.keySet();
		Future<List<Response>> future = null;
		for(PlugLoadInformationPacket infoPacket : informationSet){
			PlugLoadInstructionPacket instructionPacket= new PlugLoadInstructionPacket(infoPacket.getMacId(), "on");
			String Ip= infoPacket.getIpAddress();
			if(instructionMap.get(Ip)==null){
				ArrayList<PlugLoadInstructionPacket>plugLoadForIp= new ArrayList<>();
				plugLoadForIp.add(instructionPacket);
			    instructionMap.put(Ip,plugLoadForIp);
			}
			else{
				ArrayList<PlugLoadInstructionPacket>plugLoadForIp=instructionMap.get(Ip);
				plugLoadForIp.add(instructionPacket);
				instructionMap.put(Ip,plugLoadForIp);
			}
		}
		ExecutorService executorService = Executors.newFixedThreadPool(INSTRUCTION_SERVICE_THREAD_POOL);
		List<PlugLoadInstructionPacket>plugInstructionPacket= new ArrayList<>();
		Set<String> ipSet=instructionMap.keySet();
		for (String ip : ipSet) {
			plugInstructionPacket = instructionMap.get(ip);
			List<Response> responseList = new ArrayList<>();
			// DcsMultipleInstructionService dcsMultipleInstructionService= new
			// DcsMultipleInstructionService(plugInstructionPacket);
			future=creatingThread(plugInstructionPacket,executorService,future);
			
			try {
				responseList = future.get(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				Logger.error("TimeOut While Sending Instruction" + e);
				Logger.info("Exit switchOnAllLoads method ***********************"+System.currentTimeMillis());
				responseList.add(new Response("", CONNECTION_FAILURE, "Connection not Stabilise for Ip: "+ip, "") );
			}
			catch (Exception e) {
				Logger.error("Exception While Sending Instruction" + e);
				Logger.info("Exit switchOnAllLoads method ***********************"+System.currentTimeMillis());
				responseList.add(new Response("", CONNECTION_FAILURE, "Connection not Stabilise for Ip: "+ip, "") );
			}
			finalResponseList.addAll(responseList);
		}
		return finalResponseList;
	
		
	}

	/* (non-Javadoc)
	 * @see edu.simberbest.dcs.service.DcsInformationService#summaryOfAllPlugLoads()
	 */
	@Override
	public Map<String, List<String>> summaryOfAllPlugLoads() throws ApplicationException {

		Logger.info("Enter DcsInformationServiceImpl method summaryOfAllPlugLoads" );
		Map<String, List<String>>  infoMap = new HashMap<String, List<String>> ();
		List<String> tempList= new ArrayList<>();
		try {
			// Sending Collection According to Mac As All or Mac ID
				for (PlugLoadInformationPacket informationPacket : InformationProcessingService.CACHE.keySet()) {
					MacStatus macStatus=getStatusLabel(InformationProcessingService.CACHE.get(informationPacket).getRelay(),
							InformationProcessingService.CACHE.get(informationPacket).getMacId());
					tempList=infoMap.get(macStatus.getStatus());
					tempList.add(macStatus.getMacID());
					infoMap.put(macStatus.getStatus(),tempList);
				}
				Set <String>infoKey= infoMap.keySet();
				for(String s : infoKey){
					tempList=infoMap.get(s);
					infoMap.put(s+"-"+tempList.size(), tempList);
				}
			
			Logger.info("Exit DcsInformationServiceImpl method summaryOfAllPlugLoads");
		} catch (Exception e) {
			Logger.error("Exception While Fetching Data From Cache" + e);
			throw new ApplicationException(ERROR_IN_RETRIVAL, e);
		}
		return infoMap;
	}
	
	
}
