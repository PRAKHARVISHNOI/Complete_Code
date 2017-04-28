package edu.simberbest.dcs.service;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.entity.Response;
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
	public Response processInstruction(PlugLoadInstructionPacket insPct) throws ApplicationException ;
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
	/**
	 * @param instructionPackets
	 * @return 
	 * @throws ApplicationException 
	 */
	public List<Response> processMultipleInstruction(List<PlugLoadInstructionPacket> instructionPackets) throws ApplicationException;
	
	/**
	 * @return 
	 * @throws ApplicationException
	 */
	public List<Response> switchOnAllLoads() throws ApplicationException;
	/**
	 * @return
	 * @throws ApplicationException 
	 */
	public Map<String, List<String>> summaryOfAllPlugLoads() throws ApplicationException;
	
}
