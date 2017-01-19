package edu.simberbest.dcs.entity;

/**
 * @author sbbpvi
 *
 *pojo used to contain instruction from system
 */
public class InstructionPacket {
	private String macId;
	private String instruction;

	@Override
	public String toString() {
		return "/control/" + macId + "/" + instruction ;
	}
	public InstructionPacket() {
		super();
	}
	
	public InstructionPacket(String macId, String instruction) {
		super();
		this.macId = macId;
		this.instruction = instruction;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instruction == null) ? 0 : instruction.hashCode());
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
		InstructionPacket other = (InstructionPacket) obj;
		if (instruction == null) {
			if (other.instruction != null)
				return false;
		} else if (!instruction.equals(other.instruction))
			return false;
		if (macId == null) {
			if (other.macId != null)
				return false;
		} else if (!macId.equals(other.macId))
			return false;
		return true;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
}