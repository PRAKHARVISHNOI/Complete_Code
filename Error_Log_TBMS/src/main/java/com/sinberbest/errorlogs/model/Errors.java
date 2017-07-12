/**
 * 
 */
package com.sinberbest.errorlogs.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Minal Bagade
 *
 */
@Entity
@Table(name="errors")
public class Errors {
	
	@Id
	@Column(name="ID")
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Integer errorId;
	
	@Column(name="TIME")
	private Timestamp errorTime;
	
	@Column(name="EXCEPTION_TRACE")
	private String errorTrace;
	
	@Column(name="SOURCE")
	private String source;	
	
	@ManyToOne
    @JoinColumn(name="DEVICE_ID", nullable=false)
    private Device device;
	
	@Column(name="SEVERITY")
	private String severity;
	
	@Column(name="CODE")
	private String errorCode;
	
	@Column(name="DETAILS")
	private String details;
	

	public Errors() {
		super();
	}


	public Errors(int errorId, Timestamp errorTime, String errorTrace, String module, Device device, String severity,
			String errorCode, String details) {
		super();
		this.errorId = errorId;
		this.errorTime = errorTime;
		this.errorTrace = errorTrace;
		this.source = module;
		this.device = device;
		this.severity = severity;
		this.errorCode = errorCode;
		this.details = details;
	}



	public Integer getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	public Timestamp getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Timestamp errorTime) {
		this.errorTime = errorTime;
	}

	public String getErrorTrace() {
		return errorTrace;
	}

	public void setErrorTrace(String errorTrace) {
		this.errorTrace = errorTrace;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}
