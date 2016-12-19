package com.onedirect.app.appproperties.dto;

import java.io.Serializable;

public class PropertyDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -239002878568644896L;
	private String key;
	private String value;
	private Integer recordStatus;
	
	public Integer getRecorStatus() {
		return recordStatus;
	}
	public void setRecorStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
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
		return "PropertyDTO [key=" + key + ", value=" + value + "]";
	}

}
