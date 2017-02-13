package edu.simberbest.dcs.entity;

import java.io.Serializable;
import java.util.Collection;

public class TransactionPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<Object> list;
	private Response message;

	

	

	public Collection<Object> getList() {
		return list;
	}

	public void setList(Collection<Object> list) {
		this.list = list;
	}

	public Response getMessage() {
		return message;
	}

	public void setMessage(Response message) {
		this.message = message;
	}

	public TransactionPacket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionPacket(Collection<Object> infoList, Response message) {
		super();
		this.list = infoList;
		this.message = message;
	}

	

}
