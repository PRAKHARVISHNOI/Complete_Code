package com.sinberbest.poc;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;
import com.sinberbest.entity.Employee;

@Controller
public class POCController {

	@RequestMapping(value = "/signup", method = RequestMethod.POST, headers="Accept=application/xml")
	ResponseEntity<?> createAttribute( @RequestBody Employee value) throws IOException, SAXException, ParserConfigurationException {
		System.out.println(value);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
