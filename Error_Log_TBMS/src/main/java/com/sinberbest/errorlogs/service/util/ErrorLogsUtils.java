/**
 * 
 */
package com.sinberbest.errorlogs.service.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Minal Bagade
 *
 */
public class ErrorLogsUtils {
	
	private static final Logger sLogger = LoggerFactory.getLogger(ErrorLogsUtils.class);

	public static long getPollTime(int minutes, int seconds) {
		if (minutes > 0) {
			return (minutes * 60 * 1000);
		} else if (seconds > 0) {
			return seconds * 1000;
		}
		return 3000;
	}

	
	// get request headers
	public static void getHeadersInfo(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println(key + " : " + value);
			map.put(key, value);
		}
	}
}
