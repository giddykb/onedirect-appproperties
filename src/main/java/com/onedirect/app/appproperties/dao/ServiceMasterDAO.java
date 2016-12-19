package com.onedirect.app.appproperties.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onedirect.app.appproperties.entity.ServiceMaster;

@Repository
public interface ServiceMasterDAO extends CrudRepository<ServiceMaster,Long> {


	@Query("select t from ServiceMaster t where t.recordStatus=1")
	public List<ServiceMaster> fetchActiveServiceList() throws Exception;
	
	public ServiceMaster findById(Integer id);
	
	
}
