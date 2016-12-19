package com.onedirect.app.appproperties.dto;

import java.io.Serializable;

public class ServicePropertyRespDTO implements Serializable{

	private int serviceMasterId;
	private String key;
	private String value;
	private int recordStatus;
	
	public int getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}
	public int getServiceMasterId() {
		return serviceMasterId;
	}
	public void setServiceMasterId(int serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "ServicePropertyRespDTO [serviceMasterId=" + serviceMasterId + ", key=" + key + ", value=" + value + "]";
	}
	
	
}
