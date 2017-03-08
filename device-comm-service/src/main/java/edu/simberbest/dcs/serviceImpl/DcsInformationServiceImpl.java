package edu.simberbest.dcs.serviceImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.dao.DcsInformationDao;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImpl;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImplForPi;
import edu.simberbest.dcs.entity.MacStatus;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
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

	@Value("${INSTRUCTION_SERVICE_THREAD_POOL}")
	public Integer INSTRUCTION_SERVICE_THREAD_POOL;
	@Value("${CONNECTION_FAILURE}")
	public String CONNECTION_FAILURE;
	@Value("${ERROR_IN_RETRIVAL}")
	public String ERROR_IN_RETRIVAL;
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
	public String processInstruction(PlugLoadInstructionPacket instructionPacket) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method processInstruction: Param # " + instructionPacket);
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
			flag = future.get(20000, TimeUnit.MILLISECONDS);
			// flag = future.get();
		} catch (Exception e) {
			Logger.error("Exception While Sending Instruction" + e);
			throw new ApplicationException(CONNECTION_FAILURE, e);
		}
		Logger.info("Exit DcsInformationServiceImpl method processInstruction");
		return flag;

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
}
