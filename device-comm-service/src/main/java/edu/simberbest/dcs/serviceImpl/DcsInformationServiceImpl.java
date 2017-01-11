package edu.simberbest.dcs.serviceImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import edu.simberbest.dcs.dao.DcsInformationDao;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImpl;
import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.entity.InstructionPacket;
import edu.simberbest.dcs.exception.ApplicationException;
import edu.simberbest.dcs.exception.DaoException;
import edu.simberbest.dcs.service.DcsInformationService;

public class DcsInformationServiceImpl implements DcsInformationService {

	
	 @Autowired
	 private DcsInformationDao dataServiceDao;
	static LinkedList<InstructionPacket> insQueue=new LinkedList<>();
	
	
	@Override
	public boolean insertCurrentFeedToPie(InformationPacket infoPcket) throws ApplicationException {
		try {
			new DcsInformationDaoImpl().insertCurrentFeedToPie(infoPcket);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ApplicationException(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean processInstruction(InstructionPacket insPct) throws ApplicationException {
		boolean flag=false;
		try{
		insQueue.add(insPct);
		ExecutorService executorService= Executors.newFixedThreadPool(8);
		for(InstructionPacket ins : insQueue){
           	executorService.execute(new DcsClientTask(ins));
           	flag=	insQueue.remove(ins);
		}
		}catch(Exception e){
			throw new ApplicationException(e.getMessage());
		}
		return flag;
	
			
	}

	@Override
	public Collection<InformationPacket> getDetails(String mcId) throws ApplicationException {
		Collection<InformationPacket> infoList= new LinkedList<>();
		try{
		
		if(mcId.equals("all")){
			for (String mac :InformationProcessingService.inmformationMap.keySet()) {
				infoList.add(InformationProcessingService.inmformationMap.get(mac));
			}
		}
		else{
			for (String mac :InformationProcessingService.inmformationMap.keySet()) {
				if(mac.split("##")[1].equals(mcId))
				infoList.add(InformationProcessingService.inmformationMap.get(mac));
			}
		}
		
		}catch(Exception e){
			throw new ApplicationException(e.getMessage());
		}
		return infoList;
	}

}
