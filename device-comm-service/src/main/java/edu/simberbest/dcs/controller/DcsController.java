package edu.simberbest.dcs.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.entity.Message;
import edu.simberbest.dcs.entity.TransactionPacket;
import edu.simberbest.dcs.exception.ApplicationException;
import edu.simberbest.dcs.service.DcsInformationService;

@RestController
@RequestMapping(value = "/dcs")
public class DcsController {

	@Autowired
	DcsInformationService dataService;
	private static final Logger Logger = LoggerFactory.getLogger(DcsController.class);

	/**
	 * @param insPct
	 * @return
	 * 
	 * Method To process Instruction, Send Message as status code depending on out put
	 */
	@RequestMapping(value = "/processInstruction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> processInstruction(@RequestBody PlugLoadInstructionPacket instructionPacket) {
		Logger.info("Enter DataController method processInstruction: Param # " + instructionPacket);
		try {
			// DcsInformationServiceImpl.instructionQueue.add(instructionPacket);
			String flag;
			flag = dataService.processInstruction(instructionPacket);
			Logger.info("Exit DataController method processInstruction");
			return new ResponseEntity(new Message(flag), HttpStatus.OK);
		} catch (ApplicationException e) {
			Logger.error("Exception Occured DataController method processInstruction: Error code " + e.getMessage());
			return new ResponseEntity(new Message(e.getMessage()), HttpStatus.OK);
		}
	}

	/**
	 * @param mcId
	 * @return
	 * Method to get the information, return information and message status
	 */
	@RequestMapping(value = "/getData/{macId:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionPacket> getData(@PathVariable String macId) {
		Logger.info("Enter DataController method getData: Param # " + macId);
		TransactionPacket transactionPacket = new TransactionPacket();
		try {
			Collection<PlugLoadInformationPacket> informationPackets = dataService.getDetails(macId);
			transactionPacket.setInfoList(informationPackets);
			if (informationPackets != null && !informationPackets.isEmpty()) {
				transactionPacket.setMessage(new Message(CommunicationServiceConstants.INFORMATION_AVAILABLE));
			} else if (informationPackets == null || informationPackets.isEmpty()) {
				transactionPacket.setMessage(new Message(CommunicationServiceConstants.NO_INFORMATION_AVAILABLE));
			}
			Logger.info("Exit DataController method getData");
			return new ResponseEntity(transactionPacket, HttpStatus.OK);
		} catch (ApplicationException e) {
			transactionPacket.setMessage(new Message(e.getMessage()));
			Logger.error("Exception Occured DataController method processInstruction: Error code " + e.getMessage());
			return new ResponseEntity(transactionPacket, HttpStatus.OK);
		}

	}

}
