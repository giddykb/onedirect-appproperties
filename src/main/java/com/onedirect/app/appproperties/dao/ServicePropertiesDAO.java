package com.onedirect.app.appproperties.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onedirect.app.appproperties.dto.PropertyDTO;
import com.onedirect.app.appproperties.dto.ServicePropertyDTO;
import com.onedirect.app.appproperties.entity.ServiceProperties;

@Repository
public interface ServicePropertiesDAO extends CrudRepository<ServiceProperties,Long > {
	
	

	@Query("SELECT t from ServiceProperties t where t.serviceMasterId=:serviceMasterId ")
	public List<ServiceProperties>  fetchPropertiesOfServiceById(@Param("serviceMasterId") Integer serviceMasterId) throws Exception;
	
	public ServiceProperties findByKey(String key);
	
	@Query("SELECT t from ServiceProperties t where t.serviceMasterId=:serviceMasterId and t.key=:key")
	public ServiceProperties findByServiceMasterId(@Param("serviceMasterId") Integer serviceMasterId,@Param("key") String key) throws Exception;
	
}
