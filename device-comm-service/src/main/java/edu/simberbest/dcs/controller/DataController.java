package edu.simberbest.dcs.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.entity.InstructionPacket;
import edu.simberbest.dcs.entity.Message;
import edu.simberbest.dcs.exception.ApplicationException;
import edu.simberbest.dcs.service.DcsInformationService;

@RestController
public class DataController {

	@Autowired
	DcsInformationService dataService;

	
	
	@RequestMapping(value = "/processInput", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> processInput(@RequestBody InstructionPacket insPct) throws ApplicationException {
		System.out.println("***************" + insPct);
		// DataService dataService = new DataServiceImpl();
		boolean flag=dataService.processInstruction(insPct);
		if(flag){
			return new ResponseEntity(new Message("Successful"), HttpStatus.OK);
		}
		else{
		return new ResponseEntity(new Message("Fail"), HttpStatus.OK);}
	}

	@RequestMapping(value = "/getData/{mcId:.+}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<InformationPacket>> getData(@PathVariable String mcId) throws ApplicationException {
		System.out.println("***************" + mcId);
		// DataService dataService = new DataServiceImpl();
		Collection<InformationPacket> informationPacket = dataService.getDetails(mcId);
		return new ResponseEntity<Collection<InformationPacket>>(informationPacket, HttpStatus.OK);
	}

}
