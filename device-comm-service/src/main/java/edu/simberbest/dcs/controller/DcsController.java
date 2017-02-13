package edu.simberbest.dcs.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import edu.simberbest.dcs.entity.Response;
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
	 * Method To process Instruction, Send Message as status code depending on out put future
	 */
	@RequestMapping(value = "/processInstruction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> processInstruction(@RequestBody PlugLoadInstructionPacket instructionPacket) {
		Logger.info("Enter DataController method processInstruction: Param # " + instructionPacket);
		try {
			// DcsInformationServiceImpl.instructionQueue.add(instructionPacket);
			String flag;
			flag = dataService.processInstruction(instructionPacket);
			Logger.info("Exit DataController method processInstruction");
			if(flag.equals("DCS404")){
				return new ResponseEntity(new Response(flag,CommunicationServiceConstants.NOT_FOUND_DETAILS), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity(new Response(flag,CommunicationServiceConstants.EXECUTION_DETAILS), HttpStatus.OK);
		} catch (ApplicationException e) {
			Logger.error("Exception Occured DataController method processInstruction: Error code " + e.getMessage());
			return new ResponseEntity(new Response(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * @param instructionPacket
	 * @return
	 * 
	 * Method to process multiple processing request
	 */
	@RequestMapping(value = "/processMultipleInstruction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Response>> processMultipleInstruction(@RequestBody List<PlugLoadInstructionPacket> instructionPackets) {
		Logger.info("Enter DataController method processMultipleInstruction: Param # " + instructionPackets);
		List<String> outputFlag=new  ArrayList<String>(); String flag = null;
		List<Response>responseList = new  ArrayList<Response>();
		try{
		for (PlugLoadInstructionPacket instructionPacket : instructionPackets) {
			try {
					flag = dataService.processInstruction(instructionPacket);
					Logger.info("Exit DataController method processInstruction");
					if (flag.equals("DCS404")) {
						responseList.add(new Response(flag, CommunicationServiceConstants.NOT_FOUND_DETAILS));
					}
					else{
					responseList.add(new Response(flag,CommunicationServiceConstants.EXECUTION_DETAILS));
					}
				
			} catch (ApplicationException e) {
				Logger.error("Exception Occured DataController method processInstruction: Error code " + e.getMessage());
				responseList.add(new Response(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS));
			}
		}
		}
		catch (Exception e) {
			Logger.error("Exception Occured DataController method processInstruction: Error code " + e.getMessage());
			return new ResponseEntity(responseList.add(new Response(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS_FOR_ALL)),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity( responseList,HttpStatus.OK);
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
			Collection<Object> informationPackets = dataService.getDetails(macId);
			transactionPacket.setList(informationPackets);
			if (informationPackets != null && !informationPackets.isEmpty()) {
				transactionPacket.setMessage(new Response(CommunicationServiceConstants.INFORMATION_AVAILABLE,CommunicationServiceConstants.INFORMATION_AVAILABLE_DETAILS));
			} else if (informationPackets == null || informationPackets.isEmpty()) {
				transactionPacket.setMessage(new Response(CommunicationServiceConstants.NO_INFORMATION_AVAILABLE,CommunicationServiceConstants.NO_INFORMATION_AVAILABLE_DETAILS));
			}
			Logger.info("Exit DataController method getData");
			return new ResponseEntity(transactionPacket, HttpStatus.OK);
		} catch (ApplicationException e) {
			transactionPacket.setMessage(new Response(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS));
			Logger.error("Exception Occured DataController method getData: Error code " + e.getMessage());
			return new ResponseEntity(transactionPacket, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value = "/getStatus/{macId:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionPacket> getStatus(@PathVariable String macId) {
		Logger.info("Enter DataController method getStatus: Param # " + macId);
		TransactionPacket transactionPacket = new TransactionPacket();
		try {
			Collection<Object> informationPackets = dataService.getStatus(macId);
			transactionPacket.setList(informationPackets);
			if (informationPackets != null && !informationPackets.isEmpty()) {
				transactionPacket.setMessage(new Response(CommunicationServiceConstants.INFORMATION_AVAILABLE,CommunicationServiceConstants.INFORMATION_AVAILABLE_DETAILS));
			} else if (informationPackets == null || informationPackets.isEmpty()) {
				transactionPacket.setMessage(new Response(CommunicationServiceConstants.NO_INFORMATION_AVAILABLE,CommunicationServiceConstants.NO_INFORMATION_AVAILABLE_DETAILS));
			}
			Logger.info("Exit DataController method getStatus");
			return new ResponseEntity(transactionPacket, HttpStatus.OK);
		} catch (ApplicationException e) {
			transactionPacket.setMessage(new Response(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS));
			Logger.error("Exception Occured DataController method getStatus: Error code " + e.getMessage());
			return new ResponseEntity(transactionPacket, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	

}
