package com.onedirect.app.appproperties.dto;

import java.io.Serializable;

public class ServiceInfoDTO implements Serializable {

	private int serviceMasterId;
	private String serviceName;
	public int getServiceMasterId() {
		return serviceMasterId;
	}
	public void setServiceMasterId(int serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Override
	public String toString() {
		return "ServiceInfoDTO [serviceMasterId=" + serviceMasterId + ", serviceName=" + serviceName + "]";
	}
	
}
