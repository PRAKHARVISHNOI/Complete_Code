package com.simberbest.dcs.Util;

import org.springframework.beans.factory.annotation.Value;

import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.serviceImpl.InformationProcessingService;

public class Util {
	@Value("${IP_NOT_PRESENT}")
	public static String IP_NOT_PRESENT;

	public static String getIp(String mac) {

		String Ip = null;
		String message = null;
		// Getting Ip from CACHE for specific Mac
		for (PlugLoadInformationPacket informationPacket : InformationProcessingService.CACHE.keySet()) {
			if (informationPacket.getMacId().equals(mac)) {
				Ip = informationPacket.getIpAddress();
			}
		}
		if (Ip == null) {
			message = IP_NOT_PRESENT;
		}
		return Ip;
	}

}
