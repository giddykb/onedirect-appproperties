package com.onedirect.app.appproperties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.onedirect.app.appproperties.dao.ServiceMasterDAO;
import com.onedirect.app.appproperties.entity.ServiceMaster;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan({"com.onedirect.app.appproperties.*"})
public class OnedirectApppropertiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnedirectApppropertiesApplication.class, args);
	}
	/*
	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())              
		          .paths(PathSelectors.any())                          
		          .build();      
	}*/
	

//	@Bean
//	public CommandLineRunner demo(ServiceMasterDAO repository) {
//		ServiceMaster sm = new ServiceMaster();
//		ServiceMaster smt = new ServiceMaster();
//
//		sm.setId(1L);sm.setServiceName("twitter");
//		sm.setRecordStatus(1);
//		sm.setServicePropertyUrl("test.twitter");
//		
//		smt.setId(2L);smt.setServiceName("ticketing");
//		smt.setRecordStatus(1);
//		smt.setServicePropertyUrl("test.reporting");
//		return (args)->{
//			repository.save(sm);
//			repository.save(smt);
//		};
//	}	
}
