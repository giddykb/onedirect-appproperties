package com.onedirect.app.appproperties.dto;

import java.io.Serializable;
import java.util.List;

public class ServicePropertyDTO  implements Serializable{

	/**
	 * 
	 */
	
	private int serviceType;
	private List<PropertyDTO> propertiesList;

	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	public List<PropertyDTO> getPropertiesList() {
		return propertiesList;
	}
	public void setPropertiesList(List<PropertyDTO> propertiesList) {
		this.propertiesList = propertiesList;
	}
	
	@Override
	public String toString() {
		return "ServicePropertyDTO [serviceType=" + serviceType + ", propertiesList=" + propertiesList + "]";
	}
}
