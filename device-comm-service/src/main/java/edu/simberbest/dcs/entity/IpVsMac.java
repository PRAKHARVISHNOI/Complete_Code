package edu.simberbest.dcs.entity;

import java.util.List;
import java.util.Map;

public class IpVsMac {

	private String ipAddress;
	private List<String> macIds;
	private Map<String, List<String>>mapOfMacs;
	
	
	
	public Map<String, List<String>> getMapOfMacs() {
		return mapOfMacs;
	}
	public void setMapOfMacs(Map<String, List<String>> mapOfMacs) {
		this.mapOfMacs = mapOfMacs;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public List<String> getMacIds() {
		return macIds;
	}
	public void setMacIds(List<String> macIds) {
		this.macIds = macIds;
	}
	@Override
	public String toString() {
		return "IpVsMac [ipAddress=" + ipAddress + ", macIds=" + macIds + "]";
	}
	public IpVsMac(String ipAddress, List<String> macIds) {
		super();
		this.ipAddress = ipAddress;
		this.macIds = macIds;
	}
	public IpVsMac() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
