package com.onedirect.app.appproperties.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onedirect.app.appproperties.dao.ServiceMasterDAO;
import com.onedirect.app.appproperties.dto.PropertyDTO;
import com.onedirect.app.appproperties.dto.ServiceInfoDTO;
import com.onedirect.app.appproperties.dto.ServicePropertyDTO;
import com.onedirect.app.appproperties.dto.ServicePropertyRespDTO;
import com.onedirect.app.appproperties.entity.ServiceMaster;


public interface AppPropertiesService {

	public List<ServiceInfoDTO> fetchServicesList();
	
	public ServicePropertyDTO fetchPropertiesOftheService(int serviceMasterId);
	
	public ServicePropertyRespDTO putServiceProperty(int serviceMasterId,PropertyDTO propertDTO);
	
	public ServicePropertyRespDTO updateServiceProperty(int serviceMasterId,PropertyDTO propertDTO);
	
	public ServicePropertyDTO  bulkUpdateOfServiceProperties(ServicePropertyDTO servicerPropertyDTO);
	
	public ServicePropertyRespDTO toggleServiceProperty(int serviceMasterId,PropertyDTO propertDTO);
}
