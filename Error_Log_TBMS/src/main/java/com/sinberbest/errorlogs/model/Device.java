package com.sinberbest.errorlogs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;

@Entity
@Table(name="devices")
public class Device implements IModel{

	@Id
	@Column(name="ID")
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private int id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="MAC_ID")
	private String macID;
	
	@Column(name="ACTIVE")
	private Boolean active;
	
	@Column(name="CONNECTED")
	private Boolean connected;
	
	@Column(name="CREATED_TIME")
	private Timestamp createdTime;
	
	@Column(name="UPDATED_TIME")
	private Timestamp updatedTime;
	
	@Column(name="DEVICE_TYPEID")
	private int deviceTypeID;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="STATE")
	private String state;
	
	@OneToMany(mappedBy="device", cascade = CascadeType.ALL)
    private Set<Errors> errors = new HashSet<Errors>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMacID() {
		return macID;
	}

	public void setMacID(String macID) {
		this.macID = macID;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getConnected() {
		return connected;
	}

	public void setConnected(Boolean connected) {
		this.connected = connected;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getDeviceTypeID() {
		return deviceTypeID;
	}

	public void setDeviceTypeID(int deviceTypeID) {
		this.deviceTypeID = deviceTypeID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<Errors> getErrors() {
		return errors;
	}

	public void setErrors(Set<Errors> errors) {
		this.errors = errors;
	}

	
}
