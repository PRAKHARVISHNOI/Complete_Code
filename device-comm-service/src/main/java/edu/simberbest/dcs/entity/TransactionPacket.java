package edu.simberbest.dcs.entity;

import java.io.Serializable;
import java.util.Collection;

public class TransactionPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<PlugLoadInformationPacket> infoList;
	private Message message;

	public Collection<PlugLoadInformationPacket> getInfoList() {
		return infoList;
	}

	public void setInfoList(Collection<PlugLoadInformationPacket> infoList) {
		this.infoList = infoList;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public TransactionPacket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionPacket(Collection<PlugLoadInformationPacket> infoList, Message message) {
		super();
		this.infoList = infoList;
		this.message = message;
	}

}
