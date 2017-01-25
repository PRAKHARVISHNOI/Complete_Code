package edu.simberbest.dcs.serviceImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.dao.DcsInformationDao;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImpl;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImplForPi;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.exception.ApplicationException;
import edu.simberbest.dcs.exception.DaoException;
import edu.simberbest.dcs.service.DcsInformationService;

/**
 * @author sbbpvi
 * used for sending information to pi, sending instruction to socket client and getting information from cache
 */
public class DcsInformationServiceImpl implements DcsInformationService {
	private static final Logger Logger = LoggerFactory.getLogger(DcsInformationServiceImpl.class);
	@Autowired
	private DcsInformationDao dataServiceDao;
	public static volatile ConcurrentLinkedQueue<PlugLoadInstructionPacket> instructionQueue = new ConcurrentLinkedQueue<>();

	/**
	 * method to call pi data-feed
	 * (non-Javadoc)
	 * @see edu.simberbest.dcs.service.DcsInformationService#insertCurrentFeedToPie(edu.simberbest.dcs.entity.PlugLoadInformationPacket)
	 */
	@Override
	public boolean insertCurrentFeedToPie(PlugLoadInformationPacket infoPcket) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method insertCurrentFeedToPie: Param # "+infoPcket);
		try {
			new DcsInformationDaoImpl().insertCurrentFeedToTextFile(infoPcket);
			new DcsInformationDaoImplForPi().insertCurrentFeedToPi(infoPcket);
		} catch (DaoException e) {
			e.printStackTrace();
			Logger.error("Exception While Data Entering In Pi"+e);
			throw new ApplicationException(e.getMessage());
		}
		Logger.info("Exit DcsInformationServiceImpl method insertCurrentFeedToPie");
		return false;
	}

	/**
	 * 
	 * method to process instruction packet 
	 * (non-Javadoc)
	 * @see edu.simberbest.dcs.service.DcsInformationService#processInstruction(edu.simberbest.dcs.entity.PlugLoadInstructionPacket)
	 */
	@Override
	public String processInstruction(PlugLoadInstructionPacket instructionPacket) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method processInstruction: Param # "+instructionPacket);
		String flag = "";
		try {
			// Async Call for Instruction
			Future<String> future = null;
			ExecutorService executorService = Executors.newFixedThreadPool(CommunicationServiceConstants.INSTRUCTION_SERVICE_THREAD_POOL);
			future = executorService.submit(new DcsClientTask(instructionPacket));
		  //	instructionQueue.remove(instructionPacket);
			flag = future.get(3000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			Logger.error("Exception While Sending Instruction"+e);
			throw new ApplicationException(CommunicationServiceConstants.CONNECTION_FAILURE,e);
		}
		Logger.info("Exit DcsInformationServiceImpl method processInstruction");
		return flag;

	}

	/**
	 * Method to get information from cache(Map)
	 * (non-Javadoc)
	 * @see edu.simberbest.dcs.service.DcsInformationService#getDetails(java.lang.String)
	 */
	@Override
	public Collection<PlugLoadInformationPacket> getDetails(String mcId) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method getDetails: Param # "+mcId);
		Collection<PlugLoadInformationPacket> infoList = new LinkedList<>();
		try {
			 // Sendig Collection According to MAc As All or Mac ID
			if (mcId.equals(CommunicationServiceConstants.ALL)) {
				for (PlugLoadInformationPacket informationPacket : InformationProcessingService.CACHE.keySet()) {
					infoList.add(InformationProcessingService.CACHE.get(informationPacket));
				}
			} else {
				infoList =  InformationProcessingService.CACHE.keySet().stream().filter(it -> it.getMacId().contains(mcId)).collect(Collectors.toSet());
			}
			Logger.info("Exit DcsInformationServiceImpl method getDetails");
		} catch (Exception e) {
			Logger.error("Exception While Fetching Data From Cache"+e);
			throw new ApplicationException(CommunicationServiceConstants.ERROR_IN_RETRIVAL,e);
		}
		return infoList;
	}
}
