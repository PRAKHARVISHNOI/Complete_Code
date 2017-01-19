package edu.simberbest.dcs.service;


import java.util.Collection;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.entity.InstructionPacket;
import edu.simberbest.dcs.exception.ApplicationException;

/**
 * @author sbbpvi
 *
 */
public interface DcsInformationService {

	/**
	 * @param insPct
	 * @return
	 * @throws ApplicationException
	 */
	public String processInstruction(InstructionPacket insPct) throws ApplicationException ;
	/**
	 * @param infoPcket
	 * @return
	 * @throws ApplicationException
	 */
	public boolean insertCurrentFeedToPie(InformationPacket infoPcket)  throws ApplicationException;
	/**
	 * @param mcId
	 * @return
	 * @throws ApplicationException
	 */
	public Collection<InformationPacket> getDetails(String mcId) throws ApplicationException; 
	
}
