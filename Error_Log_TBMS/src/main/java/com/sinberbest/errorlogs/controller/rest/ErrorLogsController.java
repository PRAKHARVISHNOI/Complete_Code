/**
 * 
 */
package com.sinberbest.errorlogs.controller.rest;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sinberbest.api.entity.ErrorLog.ErrorLog;
import com.sinberbest.api.entity.ErrorLog.ErrorLogGetResp;
import com.sinberbest.api.entity.ErrorLog.ErrorLogResp;
import com.sinberbest.api.entity.common.ErrorCode;
import com.sinberbest.api.entity.common.Severity;
import com.sinberbest.api.entity.types.ErrorLogResponse;
import com.sinberbest.errorlogs.constants.ErrorsAPIConstants;
import com.sinberbest.errorlogs.dao.utils.ObjectMapperUtil;
import com.sinberbest.errorlogs.exception.DataPersistenceException;
import com.sinberbest.errorlogs.model.Errors;
import com.sinberbest.errorlogs.service.ErrorsService;
import com.sinberbest.errorlogs.service.util.ErrorLogsUtils;

/**
 * @author Minal Bagade
 *
 */
@RestController
@RequestMapping(value = "service/errorLogs")
public class ErrorLogsController {
	
	private static final Logger sLogger = LoggerFactory.getLogger(ErrorHandlerController.class);
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	ErrorsService errorService;
	
	
	
/*	@RequestMapping(value = "/signup", method = RequestMethod.POST, headers="Accept=application/xml")
	ResponseEntity<?> createAttribute( @RequestBody Purchase value) throws IOException, SAXException, ParserConfigurationException {
		System.out.println("111111");
		System.out.println(value);
		 return new ResponseEntity(new Message("Pass", "Pass"),HttpStatus.OK);
	}
	*/
	
	@RequestMapping(value = "/addErrorLog", method = RequestMethod.POST, headers = {"Accept=application/xml"},produces = MediaType.APPLICATION_XML_VALUE)
    ErrorLogResponse addErrorlog(HttpServletRequest httpServletRequest, HttpServletResponse response, 
    		@RequestBody ErrorLog errorlog ) {

		ErrorLogsUtils.getHeadersInfo(httpServletRequest);
		Errors errorEntity = ObjectMapperUtil.getMappedObjectForAddErrorlog(errorlog);
		Errors addedErrorlog = null;ErrorLogResp resp = new ErrorLogResp();
		try{
			 addedErrorlog = errorService.addErrorLog(errorEntity, errorlog.getDeviceID());
		}
		catch(DataPersistenceException ex){
			resp.setErrorID(-1);
			resp.setErrorCode(ErrorsAPIConstants.DATABASE_ERROR_CODE);
			resp.setMessageEn(messageSource.getMessage("api.errorlog.save.error", null, Locale.getDefault()));
		}
		sLogger.info("Constructing the response for addErrorlog API.");
		if(addedErrorlog==null){
			resp.setErrorCode(ErrorsAPIConstants.PROC_ERROR_CODE);
			resp.setMessageEn(messageSource.getMessage("api.errorlog.not.added", null, Locale.getDefault()));
		}else if(addedErrorlog.getErrorId()!=null){
			resp.setErrorID(addedErrorlog.getErrorId());
		}
		return resp;
		
	}
	
	@RequestMapping(value = "/getAllErrors", method = RequestMethod.GET, headers = {"Accept=application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ErrorLogResponse getAllErrors(HttpServletRequest httpServletRequest, HttpServletResponse response) {

		ErrorLogsUtils.getHeadersInfo(httpServletRequest);
		
		List<Errors> errorlogsList = errorService.getAllErrorLogs();
		
		sLogger.info("Constructing the response for getAllErrors API.");
		ErrorLogGetResp getResponse = new ErrorLogGetResp();
				
		for(Errors error: errorlogsList){
			getResponse.getError().add(ObjectMapperUtil.getMappedObjectForError(error));
		}
		
		return getResponse;
		
	}
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, headers = {"Accept=application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ErrorLog test(HttpServletRequest httpServletRequest, HttpServletResponse response) {

		
		sLogger.info("Constructing the response for getAllErrors API.");
		ErrorLog getResponse = new ErrorLog();
	
		getResponse.setDetails("details");
		getResponse.setDeviceID(1);
		getResponse.setErrorCode(ErrorCode.E_11);
		getResponse.setErrorTrace("fewgr");
		getResponse.setSeverity(Severity.CRITICAL);
		getResponse.setSource("dgfergtr");
		
		return getResponse;
		
	}
}



/**
 * 
 */
