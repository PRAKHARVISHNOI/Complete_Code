package edu.simberbest.dcs.entity;

/**
 * @author sbbpvi
 *
 */
public class InformationPacket {

	private String ipAddress;
	private String macId;
	private String timestamp;
	private String power;
	private String energy;
	private String relay;
	
	@Override
	public String toString() {
		return "InformationPacket [ipAddress=" + ipAddress + ", macId=" + macId + ", timestamp=" + timestamp
				+ ", power=" + power + ", energy=" + energy + ", relay=" + relay + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((energy == null) ? 0 : energy.hashCode());
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + ((macId == null) ? 0 : macId.hashCode());
		result = prime * result + ((power == null) ? 0 : power.hashCode());
		result = prime * result + ((relay == null) ? 0 : relay.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		if (energy == null) {
			if (other.energy != null)
				return false;
		} else if (!energy.equals(other.energy))
			return false;
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
		if (power == null) {
			if (other.power != null)
				return false;
		} else if (!power.equals(other.power))
			return false;
		if (relay == null) {
			if (other.relay != null)
				return false;
		} else if (!relay.equals(other.relay))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
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
	public InformationPacket(String ipAddress, String macId, String timestamp, String power, String energy,
			String relay) {
		super();
		this.ipAddress = ipAddress;
		this.macId = macId;
		this.timestamp = timestamp;
		this.power = power;
		this.energy = energy;
		this.relay = relay;
	}
	public InformationPacket() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
