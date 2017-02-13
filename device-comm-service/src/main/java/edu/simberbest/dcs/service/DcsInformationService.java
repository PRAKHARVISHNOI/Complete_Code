package edu.simberbest.dcs.service;


import java.util.Collection;

import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
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
	public String processInstruction(PlugLoadInstructionPacket insPct) throws ApplicationException ;
	/**
	 * @param infoPcket
	 * @return
	 * @throws ApplicationException
	 */
	public boolean insertCurrentFeedToPie(PlugLoadInformationPacket infoPcket)  throws ApplicationException;
	/**
	 * @param mcId
	 * @return
	 * @throws ApplicationException
	 */
	public Collection<Object> getDetails(String mcId) throws ApplicationException;
	/**
	 * @param macId
	 * @return
	 */
	public Collection<Object> getStatus(String macId)throws ApplicationException; 
	
}
