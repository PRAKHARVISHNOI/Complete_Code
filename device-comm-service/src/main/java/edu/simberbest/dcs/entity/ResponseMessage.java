package edu.simberbest.dcs.entity;

public class ResponseMessage {
	private String messageCode;
	private String messageDetails;
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getMessageDetails() {
		return messageDetails;
	}
	public void setMessageDetails(String messageDetails) {
		this.messageDetails = messageDetails;
	}
	public ResponseMessage(String messageCode, String messageDetails) {
		super();
		this.messageCode = messageCode;
		this.messageDetails = messageDetails;
	}
	public ResponseMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
