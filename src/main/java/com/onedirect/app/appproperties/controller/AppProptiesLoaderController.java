package com.onedirect.app.appproperties.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onedirect.app.appproperties.dto.ServiceInfoDTO;
import com.onedirect.app.appproperties.entity.ServiceProperties;
import com.onedirect.app.appproperties.exception.CustomAppException;
import com.onedirect.app.appproperties.exception.ErrorCodes;
import com.onedirect.app.appproperties.service.AppPropertiesLoaderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="/v1")
@RestController
@RequestMapping(value="/v1")
public class AppProptiesLoaderController {

	@Autowired
	private AppPropertiesLoaderService appPropertiesLoaderService;
	
	
	@ApiOperation(value="Refresh service properties in redis ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of properties cached", response = java.util.List.class) ,
			@ApiResponse(code=404, message ="No properties available", response = String.class),
			@ApiResponse(code=500, message="Internal Server Error", response = String.class)
			})
	@RequestMapping(value="/reload/{id}",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ServiceProperties>> reloadPropertiesInRedis(@PathVariable Integer id) throws CustomAppException {
	
		List<ServiceProperties> servicePropertiesList = appPropertiesLoaderService.reloadAppProperties(id);
		
		if(null ==servicePropertiesList)	
			throw new CustomAppException(ErrorCodes.TS_4000, ErrorCodes.TS_4000_MSG);
		return new ResponseEntity<>(servicePropertiesList,HttpStatus.OK);
	}
	
	@ApiOperation(value="Bulk Refresh of service properties in redis ")
	@RequestMapping(value="/bulk/reload",method=RequestMethod.POST)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of properties cached", response = java.util.List.class) ,
			@ApiResponse(code=404, message ="No properties available", response = String.class),
			@ApiResponse(code=500, message="Internal Server Error", response = String.class)
			})
	public ResponseEntity<String> bulkreloadPropertiesInRedis(@RequestBody List<ServiceInfoDTO> reqServiceInfoDTO) throws CustomAppException{
		
		if(null ==reqServiceInfoDTO)	
			throw new CustomAppException(ErrorCodes.TS_4000, ErrorCodes.TS_4000_MSG);
		
		appPropertiesLoaderService.bulkrelodAppProperties(reqServiceInfoDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
