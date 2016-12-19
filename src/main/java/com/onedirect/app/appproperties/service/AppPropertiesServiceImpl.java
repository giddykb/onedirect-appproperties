package com.onedirect.app.appproperties.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onedirect.app.appproperties.dao.ServiceMasterDAO;
import com.onedirect.app.appproperties.dao.ServicePropertiesDAO;
import com.onedirect.app.appproperties.dto.PropertyDTO;
import com.onedirect.app.appproperties.dto.ServiceInfoDTO;
import com.onedirect.app.appproperties.dto.ServicePropertyDTO;
import com.onedirect.app.appproperties.dto.ServicePropertyRespDTO;
import com.onedirect.app.appproperties.entity.ServiceMaster;
import com.onedirect.app.appproperties.entity.ServiceProperties;
import com.onedirect.app.appproperties.exception.CustomAppException;
import com.onedirect.app.appproperties.exception.ErrorCodes;
import com.onedirect.app.appproperties.util.DateHelper;


@Service("appPropertiesService")
public class AppPropertiesServiceImpl  implements AppPropertiesService{

	@Autowired
	private ServiceMasterDAO serviceMasterDAO;
	
	@Autowired
	private ServicePropertiesDAO servicePropertiesDAO;
	
	
	private Logger logger = LogManager.getLogger(AppPropertiesServiceImpl.class);
	
	@Transactional(readOnly=true)
	public List<ServiceInfoDTO> fetchServicesList(){
		
		List<ServiceInfoDTO> servicesInfoList = new ArrayList<>();
		try{
		
			List<ServiceMaster> listOfServices =(List<ServiceMaster>) serviceMasterDAO.fetchActiveServiceList();
			processServiceInfoRespList(servicesInfoList, listOfServices);
			logger.info("List of all Active services :{} ",servicesInfoList);

		}
		catch(Exception e){
			logger.error("Exception while fetching all the services: {}",ExceptionUtils.getStackTrace(e));	
			}
		return servicesInfoList;
		
	}
	
	private void processServiceInfoRespList(List<ServiceInfoDTO> servicesInfoList,List<ServiceMaster> listOfServices){
		
		if(null!=listOfServices && listOfServices.size()>0){ 
			for(ServiceMaster serviceInfo:listOfServices){
				String serviceName =serviceInfo.getServiceName();
				Integer serviceMasterId = serviceInfo.getId();
				if(null!=serviceName){
					ServiceInfoDTO  serviceInfoDTO = new ServiceInfoDTO();
					serviceInfoDTO.setServiceMasterId(serviceMasterId);
					serviceInfoDTO.setServiceName(serviceName);
					servicesInfoList.add(serviceInfoDTO);
				}
			}
		}
		else{
			logger.error("List of active services is empty ");
			throw new CustomAppException(ErrorCodes.TS_4000, ErrorCodes.TS_4000_MSG);
		}
	}

	@Override
	public ServicePropertyDTO fetchPropertiesOftheService(int serviceMasterId){
		// TODO Auto-generated method stub
		ServicePropertyDTO  servicePropertiesListResp = new ServicePropertyDTO();
		try {
			List<ServiceProperties>  servicePropertiesList  =(List<ServiceProperties>) servicePropertiesDAO.fetchPropertiesOfServiceById(serviceMasterId);
			
			if(null!=servicePropertiesList && servicePropertiesList.size()>0){
				
				servicePropertiesListResp.setServiceType(serviceMasterId);
				List<PropertyDTO> propertiesList = new ArrayList<>();
			
			    for(ServiceProperties serviceProperty:servicePropertiesList){
					 String key  = serviceProperty.getKey();
					 String value = serviceProperty.getValue();
					 int recordStatus = serviceProperty.getRecordStatus();
					 PropertyDTO property = new PropertyDTO();

					 property.setKey(key);
					 property.setValue(value);
					 property.setRecorStatus(recordStatus);
					 propertiesList.add(property);
					 
				 }
				servicePropertiesListResp.setPropertiesList(propertiesList);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception while fetching properties of  services: {}",ExceptionUtils.getStackTrace(e));	
		}
		
		return servicePropertiesListResp;
	}
	
	@Override
	public ServicePropertyRespDTO putServiceProperty(int serviceMasterId, PropertyDTO propertyDTO) {
		// TODO Auto-generated method stub
		ServicePropertyRespDTO  servicePropResDTO = new ServicePropertyRespDTO();

		try{
			ServiceMaster isServiceExist=serviceMasterDAO.findById(serviceMasterId);
			if(isServiceExist==null){
				throw new CustomAppException(ErrorCodes.TS_4002, ErrorCodes.TS_4002_MSG);
			}
		
			ServiceProperties  iskeyExists =servicePropertiesDAO.findByKey(propertyDTO.getKey());
			if(null!=iskeyExists){
				throw new CustomAppException(ErrorCodes.TS_4003, ErrorCodes.TS_4003_MSG);
			}

			else{
				ServiceProperties serviceProperty = new ServiceProperties();
				serviceProperty.setServiceMasterId(serviceMasterId);
				serviceProperty.setKey(propertyDTO.getKey());
				serviceProperty.setValue(propertyDTO.getValue());
				serviceProperty.setCreatedAt(DateHelper.getCurrentDateTime());
				serviceProperty.setUpdatedAt(DateHelper.getCurrentDateTime());
				serviceProperty.setRecordStatus(1);
			
				servicePropertiesDAO.save(serviceProperty);
				
				BeanUtils.copyProperties(serviceProperty, servicePropResDTO);
			}
		}
		catch(Exception e){
			logger.error("Exception while creating  the service: {}",ExceptionUtils.getStackTrace(e));	
		}
		
		return servicePropResDTO;
	}

	@Override
	public ServicePropertyRespDTO updateServiceProperty(int serviceMasterId, PropertyDTO propertyDTO) {
		// TODO Auto-generated method stub
		
		ServicePropertyRespDTO  servicePropResDTO = new ServicePropertyRespDTO();
		ServiceProperties reqServiceProperty;
		try {
			reqServiceProperty = servicePropertiesDAO.findByServiceMasterId(serviceMasterId,propertyDTO.getKey());
			
			if(reqServiceProperty!=null){
				logger.info("Service property to be updated :{}",reqServiceProperty);
				reqServiceProperty.setValue(propertyDTO.getValue());
				reqServiceProperty.setUpdatedAt(DateHelper.getCurrentDateTime());
				servicePropertiesDAO.save(reqServiceProperty);
				
				BeanUtils.copyProperties(reqServiceProperty, servicePropResDTO);
				logger.info("Updated property of the service :{}",reqServiceProperty);
			}
			else{
				throw new CustomAppException(ErrorCodes.TS_4004, ErrorCodes.TS_4004_MSG);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception while updating the service: {} , Property: {} , stackTrace:{}",serviceMasterId,propertyDTO,ExceptionUtils.getStackTrace(e));	
		}
		
		return servicePropResDTO;
	}

	@Override
	public ServicePropertyDTO bulkUpdateOfServiceProperties(ServicePropertyDTO servicerPropertyDTO) {
		// TODO Auto-generated method stub
		
		ServicePropertyDTO servicePropResDTO = new ServicePropertyDTO();
		
		try {
			Integer serviceMasterId = servicerPropertyDTO.getServiceType();
			List<PropertyDTO> propertyDTOList = servicerPropertyDTO.getPropertiesList();
			
			if(null==propertyDTOList || propertyDTOList.isEmpty())
				throw new CustomAppException(ErrorCodes.TS_4004, ErrorCodes.TS_4004_MSG);
			else{
				logger.info("Bulk Update of the service: {}",serviceMasterId);
				for(PropertyDTO propertyDTO:propertyDTOList){
					updateServiceProperty(serviceMasterId, propertyDTO);
				}
			}
			servicePropResDTO = this.fetchPropertiesOftheService(serviceMasterId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception while bulk updating : {}",ExceptionUtils.getStackTrace(e));	

		}
		return servicePropResDTO;
	}

	@Override
	public ServicePropertyRespDTO toggleServiceProperty(int serviceMasterId, PropertyDTO propertyDTO) {
		// TODO Auto-generated method stub
		ServicePropertyRespDTO  servicePropResDTO = new ServicePropertyRespDTO();

		try {
			ServiceProperties reqServiceProp = servicePropertiesDAO.findByServiceMasterId(serviceMasterId, propertyDTO.getKey());
			int recordStatus = propertyDTO.getRecorStatus();
			//int updateStatus = recordStatus==1?0:1;
			reqServiceProp.setRecordStatus(recordStatus);
			servicePropertiesDAO.save(reqServiceProp);
			
			BeanUtils.copyProperties(reqServiceProp, servicePropResDTO);
			logger.info("Updated record status  of the service :{}",reqServiceProp);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception while updating the service status : {}",ExceptionUtils.getStackTrace(e));	
		}
		

		return servicePropResDTO;
	}
	
	
}

