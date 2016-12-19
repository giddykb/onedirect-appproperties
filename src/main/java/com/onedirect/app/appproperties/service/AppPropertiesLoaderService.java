package com.onedirect.app.appproperties.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.onedirect.app.appproperties.dao.ServiceMasterDAO;
import com.onedirect.app.appproperties.dao.ServicePropertiesDAO;
import com.onedirect.app.appproperties.dto.ServiceInfoDTO;
import com.onedirect.app.appproperties.entity.ServiceMaster;
import com.onedirect.app.appproperties.entity.ServiceProperties;
import com.onedirect.app.appproperties.exception.CustomAppException;
import com.onedirect.app.appproperties.exception.ErrorCodes;


@Service("appPropertiesLoader")
public class AppPropertiesLoaderService {

	@Autowired
	private ServiceMasterDAO serviceMasterDAO;
	
	@Autowired
	private ServicePropertiesDAO servicePropertiesDAO;
	
	private static final Logger logger = LogManager.getLogger(AppPropertiesLoaderService.class);
	
	public List<ServiceProperties> reloadAppProperties(Integer serviceMasterId){
	
		ServiceMaster serviceMaster = serviceMasterDAO.findById(serviceMasterId);
		List<ServiceProperties> servicePropertiesList = new ArrayList<>();
		
		if(null!=serviceMaster){
			String apiName = serviceMaster.getApiName();
			if(apiName == null || StringUtils.isEmpty(apiName)){
				logger.error("Api Name is null");
				throw new RuntimeException("Api Name cannot be empty or null");
			}
			else{
				//String.join("/", apiName,"v1","property","reolad");
				
				try{
					StringBuilder sb = new StringBuilder();
					//sb.append("http://localhost:8080/v1/home");
					RequestAttributes requestAttributes = (RequestAttributes) RequestContextHolder.getRequestAttributes();
					if(null != requestAttributes && requestAttributes instanceof ServletRequestAttributes) {
						  HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
						  String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
						  sb.append(baseUrl);
						  sb.append(apiName);
						  sb.append("/v1/property/reload");
					}
					String url = sb.toString();
					logger.info("Reloding app properties of the service : {} and url is :{}",apiName,url);
					
					RestTemplate restTemplate = new RestTemplate();
					String res =restTemplate.postForObject(url, "", String.class, "");
					servicePropertiesList = servicePropertiesDAO.fetchPropertiesOfServiceById(serviceMasterId);

				}
				catch(HttpClientErrorException e){
					
				    logger.error("error "+ e.getResponseBodyAsString());

				} catch (Exception e) {
					// TODO Auto-generated catch block
				    logger.error("error",e.getMessage());
				}
			}
		}
		else{
			logger.error("Requested service does not exist");
			throw new CustomAppException(ErrorCodes.TS_4002, ErrorCodes.TS_4002_MSG);
		}
		
		return servicePropertiesList;
	}
	
	
	public void bulkrelodAppProperties(List<ServiceInfoDTO> serviceInfoDTOList){
		
		if(null!= serviceInfoDTOList || ! serviceInfoDTOList.isEmpty()){
			
			for(ServiceInfoDTO serviceInfoDTO : serviceInfoDTOList){
				 Integer serviceMasterId = serviceInfoDTO.getServiceMasterId();
				 reloadAppProperties(serviceMasterId);
			}
		}
		else{
			logger.error("Request for bulk refresh cannot be an empty list");
			throw new IllegalArgumentException("Request cannot be empty list or null");
		}
	}
}
