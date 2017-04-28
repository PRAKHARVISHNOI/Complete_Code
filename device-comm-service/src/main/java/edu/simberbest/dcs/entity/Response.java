package edu.simberbest.dcs.entity;

/**
 * @author sbbpvi
 * pojo used to contain message for rest call
 */
public class Response {

	private String state;
	private String messageCode;
	private String messageDetails;
	private String macID;
	
	
	public Response( String messageCode, String messageDetails) {
		super();
		this.messageCode = messageCode;
		this.messageDetails = messageDetails;
	}
	
	public String getMacID() {
		return macID;
	}
	public Response(String state, String messageCode, String messageDetails, String macID) {
		super();
		this.state = state;
		this.messageCode = messageCode;
		this.messageDetails = messageDetails;
		this.macID = macID;
	}
	public void setMacID(String macID) {
		this.macID = macID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
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
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
