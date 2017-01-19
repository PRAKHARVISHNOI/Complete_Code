package edu.simberbest.dcs.serviceImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.dao.DcsInformationDao;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImpl;
import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.entity.InstructionPacket;
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
	public static volatile ConcurrentLinkedQueue<InstructionPacket> instructionQueue = new ConcurrentLinkedQueue<>();

	/**
	 * method to call pi data-feed
	 * (non-Javadoc)
	 * @see edu.simberbest.dcs.service.DcsInformationService#insertCurrentFeedToPie(edu.simberbest.dcs.entity.InformationPacket)
	 */
	@Override
	public boolean insertCurrentFeedToPie(InformationPacket infoPcket) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method insertCurrentFeedToPie: Param # "+infoPcket);
		try {
			new DcsInformationDaoImpl().insertCurrentFeedToPie(infoPcket);
		} catch (DaoException e) {
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
	 * @see edu.simberbest.dcs.service.DcsInformationService#processInstruction(edu.simberbest.dcs.entity.InstructionPacket)
	 */
	@Override
	public String processInstruction(InstructionPacket instructionPacket) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method processInstruction: Param # "+instructionPacket);
		String flag = "";
		try {
			Future<String> future = null;
			ExecutorService executorService = Executors.newFixedThreadPool(CommunicationServiceConstants.INSTRUCTION_SERVICE_THREAD_POOL);
			future = executorService.submit(new DcsClientTask(instructionPacket));
		//	instructionQueue.remove(instructionPacket);
			flag = future.get();
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
	public Collection<InformationPacket> getDetails(String mcId) throws ApplicationException {
		Logger.info("Enter DcsInformationServiceImpl method getDetails: Param # "+mcId);
		Collection<InformationPacket> infoList = new LinkedList<>();
		try {
			if (mcId.equals("all")) {
				for (InformationPacket informationPacket : InformationProcessingService.inmformationMap.keySet()) {
					infoList.add(InformationProcessingService.inmformationMap.get(informationPacket));
				}
			} else {
				infoList = (List) InformationProcessingService.inmformationMap.keySet().stream().filter(it -> it.getMacId().contains(mcId)).collect(Collectors.toSet());
			}
			Logger.info("Exit DcsInformationServiceImpl method getDetails");
		} catch (Exception e) {
			Logger.error("Exception While Fetching Data From Cache"+e);
			throw new ApplicationException(CommunicationServiceConstants.ERROR_IN_RETRIVAL,e);
		}
		return infoList;
	}
}
