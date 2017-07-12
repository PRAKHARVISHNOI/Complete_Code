package com.sinberbest.errorlogs.exception.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinberbest.api.entity.types.ErrorLogResponse;
import com.sinberbest.errorlogs.constants.ErrorsAPIConstants;

/**
 * 
 * @author Minal Bagade
 */
@ControllerAdvice(basePackages = "com.sinberbest.errorlogs.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler extends AbstractApiExceptionHandler {

	/**
	 * Logger Instance
	 */
	private final Logger sLogger = LoggerFactory.getLogger(ValidationExceptionHandler.class);

	/**
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorLogResponse handleMethodArgumentNotValid(HttpServletRequest req,
			MethodArgumentNotValidException ex) {
		String errorURL = req.getRequestURL().toString();
		sLogger.debug("URL : " + errorURL);
		sLogger.warn("Error occured while processing request. Message : ", ex);

		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		return populateFieldErrors(fieldErrors, req);
	}

	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorLogResponse handleXMLException(HttpMessageNotReadableException ex) {

		String error = ex.getMessage().substring(ex.getMessage().indexOf(":")+1,ex.getMessage().length() );
		ErrorLogResponse errorlogsResponse = getGenericAPIErrorResponse();
		errorlogsResponse.setErrorCode(ErrorsAPIConstants.FORMAT_VALIDATION_ERROR_CODE);
		errorlogsResponse.setMessageEn("Invalid XML Error : " + error);
		return errorlogsResponse;
	}
	
	public ErrorLogResponse populateFieldErrors(List<FieldError> fieldErrorList, HttpServletRequest req) {
		ErrorLogResponse errorlogsResponse = getGenericAPIErrorResponse();
		errorlogsResponse.setErrorCode(ErrorsAPIConstants.GENERIC_ERROR_CODE);

		if (fieldErrorList != null && !fieldErrorList.isEmpty()) {
			errorlogsResponse.setErrorCode(ErrorsAPIConstants.FORMAT_VALIDATION_ERROR_CODE);
			StringBuilder errorMessage = new StringBuilder("Format Error in following Field(s) : ");

			for (FieldError fieldError : fieldErrorList) {

				if (fieldError.getField() != null) {
					errorMessage.append("\n{" + fieldError.getField() + " : " + fieldError.getDefaultMessage() +" } ");
				}

			}
			errorlogsResponse.setMessageEn(errorMessage.toString());
			return errorlogsResponse;
		}

		errorlogsResponse.setMessageEn("Generic Error From Handler");
		return errorlogsResponse;
	}

}
