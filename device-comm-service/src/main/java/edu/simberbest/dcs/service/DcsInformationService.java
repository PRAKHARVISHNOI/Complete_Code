package edu.simberbest.dcs.service;


import java.util.Collection;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.entity.InstructionPacket;
import edu.simberbest.dcs.exception.ApplicationException;

public interface DcsInformationService {
	public boolean processInstruction(InstructionPacket insPct) throws ApplicationException ;
	public boolean insertCurrentFeedToPie(InformationPacket infoPcket)  throws ApplicationException;
	public Collection<InformationPacket> getDetails(String mcId) throws ApplicationException; 
	
}
