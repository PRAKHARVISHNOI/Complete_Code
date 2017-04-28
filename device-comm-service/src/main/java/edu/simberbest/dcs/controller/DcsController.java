package edu.simberbest.dcs.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.entity.Response;
import edu.simberbest.dcs.entity.ResponseMessage;
import edu.simberbest.dcs.entity.TransactionPacket;
import edu.simberbest.dcs.exception.ApplicationException;
import edu.simberbest.dcs.service.DcsInformationService;

@RestController
@RequestMapping(value = "/dcs")
public class DcsController {

	@Autowired
	DcsInformationService dataService;
	
	@Value("${NO_INFORMATION_AVAILABLE}")
	public String NO_INFORMATION_AVAILABLE;

	@Value("${INFORMATION_AVAILABLE}")
	public String INFORMATION_AVAILABLE;
	
	
	@Value("${CONNECTION_FAILURE}")
	public String CONNECTION_FAILURE;
	
	private static final Logger Logger = LoggerFactory.getLogger(DcsController.class);

	/**
	 * @param insPct
	 * @return
	 * 
	 * Method To process Instruction, Send Message as status code depending on out put future
	 */
	@RequestMapping(value = "/processInstruction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> processInstruction(@RequestBody PlugLoadInstructionPacket instructionPacket) {
		Logger.info("Enter DataController method processInstruction: Param # " + instructionPacket);
		Response response = null;
		try {
			// DcsInformationServiceImpl.instructionQueue.add(instructionPacket);
			
			response = dataService.processInstruction(instructionPacket);
			Logger.info("Exit DataController method processInstruction");
			/*if(response.getState().equals("DCS404")){
				return new ResponseEntity(new Response(response.getState(),CommunicationServiceConstants.NOT_FOUND_DETAILS), HttpStatus.NOT_FOUND);
			}
			if(response.getState().equals("DCS504")){
				return new ResponseEntity(new Response(response.getState(),CommunicationServiceConstants.LOCAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
			}*/
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (ApplicationException e) {
			Logger.error("Exception Occured DataController method processInstruction: Error code " + e.getMessage());
			return new ResponseEntity(new ResponseMessage(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


	/**
	 * @param instructionPacket
	 * @return
	 * 
	 * Method to process multiple processing request
	 */
	@RequestMapping(value = "/processMultipleInstruction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> processMultipleInstruction(@RequestBody List<PlugLoadInstructionPacket> instructionPackets) {
		Logger.info("Enter DataController method processMultipleInstruction: Param # " + instructionPackets);
		List<String> outputFlag=new  ArrayList<String>();
		String flag = null;
		List<Response>responseList = new  ArrayList<Response>();
		try {
			responseList=dataService.processMultipleInstruction(instructionPackets);
		} catch (Exception e) {
			Logger.error("Exception Occured DataController method processInstruction: Error code " + e.getMessage());
			return new ResponseEntity(new ResponseMessage(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS_FOR_ALL),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		/*
		 * try{ for (PlugLoadInstructionPacket instructionPacket :
		 * instructionPackets) { try { flag =
		 * dataService.processInstruction(instructionPacket);
		 * Logger.info("Exit DataController method processInstruction"); if
		 * (flag.equals("DCS404")) { responseList.add(new Response(flag,
		 * CommunicationServiceConstants.NOT_FOUND_DETAILS)); } else{
		 * responseList.add(new
		 * Response(flag,CommunicationServiceConstants.EXECUTION_DETAILS)); }
		 * 
		 * } catch (ApplicationException e) { Logger.
		 * error("Exception Occured DataController method processInstruction: Error code "
		 * + e.getMessage()); responseList.add(new
		 * Response(e.getMessage(),CommunicationServiceConstants.
		 * INTERNAL_SERVER_ERROR_DETAILS)); } } } catch (Exception e) { Logger.
		 * error("Exception Occured DataController method processInstruction: Error code "
		 * + e.getMessage()); return new ResponseEntity(responseList.add(new
		 * Response(e.getMessage(),CommunicationServiceConstants.
		 * INTERNAL_SERVER_ERROR_DETAILS_FOR_ALL)),HttpStatus.
		 * INTERNAL_SERVER_ERROR); }
		 */
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
				transactionPacket.setMessage(new ResponseMessage(INFORMATION_AVAILABLE,CommunicationServiceConstants.INFORMATION_AVAILABLE_DETAILS));
			} else if (informationPackets == null || informationPackets.isEmpty()) {
				transactionPacket.setMessage(new ResponseMessage(NO_INFORMATION_AVAILABLE,CommunicationServiceConstants.NO_INFORMATION_AVAILABLE_DETAILS));
			}
			Logger.info("Exit DataController method getData");
			return new ResponseEntity(transactionPacket, HttpStatus.OK);
		} catch (ApplicationException e) {
			transactionPacket.setMessage(new ResponseMessage(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS));
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
				transactionPacket.setMessage(new ResponseMessage(INFORMATION_AVAILABLE,CommunicationServiceConstants.INFORMATION_AVAILABLE_DETAILS));
			} else if (informationPackets == null || informationPackets.isEmpty()) {
				transactionPacket.setMessage(new ResponseMessage(NO_INFORMATION_AVAILABLE,CommunicationServiceConstants.NO_INFORMATION_AVAILABLE_DETAILS));
			}
			Logger.info("Exit DataController method getStatus");
			return new ResponseEntity(transactionPacket, HttpStatus.OK);
		} catch (ApplicationException e) {
			transactionPacket.setMessage(new ResponseMessage(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS));
			Logger.error("Exception Occured DataController method getStatus: Error code " + e.getMessage());
			return new ResponseEntity(transactionPacket, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@RequestMapping(value = "/switchOnAllPlugLoads", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> switchOnAllLoads() {
		Logger.info("Enter DataController method switchOnAllLoads");
		List<Response>responseList = new  ArrayList<Response>();
		try {
			responseList = dataService.switchOnAllLoads();
			Logger.info("Exit DataController method switchOnAllLoads");
			return new ResponseEntity(responseList, HttpStatus.OK);
		} catch (ApplicationException e) {
			Logger.error("Exception Occured DataController method switchOnAllLoads: Error code " + e.getMessage());
			return new ResponseEntity(new ResponseMessage(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value = "/summaryOfAllPlugLoads", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> summaryOfAllPlugLoads() {
		Logger.info("Enter DataController method summaryOfAllPlugLoads" );
		Map<String, List<String>> summarisedInfoMap=new HashMap<>();
		
		try {
			summarisedInfoMap = dataService.summaryOfAllPlugLoads();
			Logger.info("Exit DataController method summaryOfAllPlugLoads");
			return new ResponseEntity(summarisedInfoMap, HttpStatus.OK);
		} catch (ApplicationException e) {
			Logger.error("Exception Occured DataController method summaryOfAllPlugLoads: Error code " + e.getMessage());
			return new ResponseEntity(new ResponseMessage(e.getMessage(),CommunicationServiceConstants.INTERNAL_SERVER_ERROR_DETAILS), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
