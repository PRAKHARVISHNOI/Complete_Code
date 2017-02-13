package edu.simberbest.dcs.entity;

/**
 * @author sbbpvi
 * pojo used to contain message for rest call
 */
public class Response {

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
	public Response(String messageCode, String messageDetails) {
		super();
		this.messageCode = messageCode;
		this.messageDetails = messageDetails;
	}
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	
	
	
	
}
