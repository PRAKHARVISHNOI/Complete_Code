package edu.simberbest.dcs.entity;

import java.io.Serializable;
import java.util.Collection;

public class TransactionPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<Object> list;
	private ResponseMessage message;

	public Collection<Object> getList() {
		return list;
	}

	public void setList(Collection<Object> list) {
		this.list = list;
	}


	public TransactionPacket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseMessage getMessage() {
		return message;
	}

	public void setMessage(ResponseMessage message) {
		this.message = message;
	}

	public TransactionPacket(Collection<Object> list, ResponseMessage message) {
		super();
		this.list = list;
		this.message = message;
	}


	

}
