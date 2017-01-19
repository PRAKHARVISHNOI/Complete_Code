package edu.simberbest.dcs.entity;

/**
 * @author sbbpvi
 *
 * pojo used to contain the plug load information from raspberry pi
 */


public class InformationPacket {

	private String ipAddress;
	private String macId;
	private String timestamp;
	private String power;
	private String energy;
	private String relay;
	private String pwTimeStamp;
	private String enTimeStamp;
	private String rlyTimeStamp;
	
	
	
	@Override
	public String toString() {
		return "InformationPacket [ipAddress=" + ipAddress + ", macId=" + macId + ", timestamp=" + timestamp
				+ ", power=" + power + ", energy=" + energy + ", relay=" + relay + ", pwTimeStamp=" + pwTimeStamp
				+ ", enTimeStamp=" + enTimeStamp + ", rlyTimeStamp=" + rlyTimeStamp + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + ((macId == null) ? 0 : macId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InformationPacket other = (InformationPacket) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (macId == null) {
			if (other.macId != null)
				return false;
		} else if (!macId.equals(other.macId))
			return false;
		return true;
	}
	
	public InformationPacket(String ipAddress, String macId) {
		super();
		this.ipAddress = ipAddress;
		this.macId = macId;
	}
	public String getPwTimeStamp() {
		return pwTimeStamp;
	}
	public void setPwTimeStamp(String pwTimeStamp) {
		this.pwTimeStamp = pwTimeStamp;
	}
	public String getEnTimeStamp() {
		return enTimeStamp;
	}
	public void setEnTimeStamp(String enTimeStamp) {
		this.enTimeStamp = enTimeStamp;
	}
	public String getRlyTimeStamp() {
		return rlyTimeStamp;
	}
	public void setRlyTimeStamp(String rlyTimeStamp) {
		this.rlyTimeStamp = rlyTimeStamp;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getEnergy() {
		return energy;
	}
	public void setEnergy(String energy) {
		this.energy = energy;
	}
	public String getRelay() {
		return relay;
	}
	public void setRelay(String relay) {
		this.relay = relay;
	}
	
	public InformationPacket() {
		super();
	}
	
}
