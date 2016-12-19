package com.onedirect.app.appproperties.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onedirect.app.appproperties.dto.PropertyDTO;
import com.onedirect.app.appproperties.dto.ServiceInfoDTO;
import com.onedirect.app.appproperties.dto.ServicePropertyDTO;
import com.onedirect.app.appproperties.dto.ServicePropertyRespDTO;
import com.onedirect.app.appproperties.exception.CustomAppException;
import com.onedirect.app.appproperties.exception.ErrorCodes;
import com.onedirect.app.appproperties.exception.ErrorResponse;
import com.onedirect.app.appproperties.exception.ServiceNotFoundException;
import com.onedirect.app.appproperties.service.AppPropertiesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value="/v1")
@RestController
@RequestMapping(value="/v1")
public class AppPropertiesController {

	@Autowired
	private AppPropertiesService appPropertiesService;
	
	
	@ApiOperation(value="Fetch all the services")
	@ApiResponses(value= {
			@ApiResponse(code=200,message="List of all Services",response =java.util.List.class),
			@ApiResponse(code=400,message="Bad request", response=ErrorResponse.class),
			@ApiResponse(code=404,message="Resource not found",response =String.class)
	})
	@RequestMapping(value="/services",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceInfoDTO> fetchAllServices() throws CustomAppException{
		
		List<ServiceInfoDTO> appServicesListResp = appPropertiesService.fetchServicesList();
		if(appServicesListResp ==null || appServicesListResp.isEmpty())
			throw new CustomAppException(ErrorCodes.TS_4000, ErrorCodes.TS_4000_MSG);
		return appServicesListResp;
		
	}		

	
	@ApiOperation(value="Fetch properties of the service by id")
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Properties of the Service",response =java.util.List.class),
			@ApiResponse(code=400,message="Bad request",response=ErrorResponse.class),
			@ApiResponse(code=404,message="Service does not exist",response =String.class)
	})
	@RequestMapping(value="/service/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServicePropertyDTO> fetchPropertiesOftheServiceById(@PathVariable("id") Integer id) throws CustomAppException{
		
		ServicePropertyDTO  servicePropertiesResp = appPropertiesService.fetchPropertiesOftheService(id);
		HttpStatus responseCode =HttpStatus.OK;
		if(servicePropertiesResp.getServiceType()==0){
			responseCode =HttpStatus.NOT_FOUND;
			throw new CustomAppException(ErrorCodes.TS_4002, ErrorCodes.TS_4002_MSG);
		}
		if(servicePropertiesResp.getPropertiesList()==null)
			throw new CustomAppException(ErrorCodes.TS_4001, ErrorCodes.TS_4001_MSG);
		return new ResponseEntity<ServicePropertyDTO>(servicePropertiesResp,responseCode);
	}

	
	
	@ApiOperation(value="Create a service property")
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Service property created"),
			@ApiResponse(code=400,message="Bad request"),
			@ApiResponse(code=404,message="Service does not exist",response =String.class)
	})
	@RequestMapping(value="/service/{id}",method=RequestMethod.POST)
	public ResponseEntity<ServicePropertyRespDTO> createServiceProperty(@PathVariable Integer id,@RequestBody PropertyDTO requestPropertyDTO) throws CustomAppException{
		
		ServicePropertyRespDTO  resp =appPropertiesService.putServiceProperty(id, requestPropertyDTO);
		if(null==resp)
			throw new CustomAppException(ErrorCodes.TS_4000, ErrorCodes.TS_4000_MSG);
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}
	
	
	@ApiOperation(value="Update service property")
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Service property updated"),
			@ApiResponse(code=400,message="Bad request"),
			@ApiResponse(code=404,message="Service does not exist")
	})
	@RequestMapping(value="/service/{id}",method=RequestMethod.PUT)
	public ResponseEntity<ServicePropertyRespDTO> updateServiceProperty(@PathVariable Integer id, @RequestBody PropertyDTO requestPropertyDTO){
		
		ServicePropertyRespDTO  resp = appPropertiesService.updateServiceProperty(id, requestPropertyDTO);
		HttpStatus responseCode =HttpStatus.OK;
		if(resp.getServiceMasterId()==0){
			responseCode =HttpStatus.NOT_FOUND;
			throw new CustomAppException(ErrorCodes.TS_4002, ErrorCodes.TS_4002_MSG);
		}
		return new ResponseEntity<>(resp,responseCode);
	}
	
	
	@ApiOperation(value="Enable or Disable a service property")
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Service property updated"),
			@ApiResponse(code=400,message="Bad request"),
			@ApiResponse(code=404,message="Service does not exist")
	})
	@RequestMapping(value="/service/toggle/{id}",method=RequestMethod.PUT)
	public ResponseEntity<ServicePropertyRespDTO> toggleServiceProperty(@PathVariable Integer id, @RequestBody PropertyDTO requestPropertyDTO) throws CustomAppException{
		
		ServicePropertyRespDTO  resp = appPropertiesService.toggleServiceProperty(id, requestPropertyDTO);
		if(null==resp)
			throw new CustomAppException(ErrorCodes.TS_4004, ErrorCodes.TS_4004_MSG);
		return new ResponseEntity<>(resp,HttpStatus.OK);		
	}

	
	@ApiOperation(value="Update multiple properties of the service")
	@ApiResponses(value={})
	@RequestMapping(value="/service/bulk/{id}",method=RequestMethod.PUT)
	public ResponseEntity<ServicePropertyDTO> updateBulkServiceProperties(@RequestBody ServicePropertyDTO requestServicePropDTO) throws CustomAppException{
		ServicePropertyDTO  resp = appPropertiesService.bulkUpdateOfServiceProperties(requestServicePropDTO);
		if(null==resp){
				throw new CustomAppException(ErrorCodes.TS_4004, ErrorCodes.TS_4004_MSG);
		}
		return new ResponseEntity<>(resp,HttpStatus.OK);		
	}
	

	
	/*@RequestMapping(value="/")
	public String delete(){
		return "delete changed";
	}*/
	
	
}
