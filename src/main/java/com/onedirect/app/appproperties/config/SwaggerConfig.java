package com.onedirect.app.appproperties.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@Profile(value={"dev","staging"})
public class SwaggerConfig {  
	
    @Bean
    public Docket api() { 
    	
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
    
    /*@Autowired
	private TypeResolver typeResolver;
 
	private ApiKey apiKey() {
		return new ApiKey("mykey", "api_key", "header");
	}
 
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/anyPath.*")).build();
	}
 
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return new ArrayList((Collection) new SecurityReference("mykey", authorizationScopes));
	}
 
	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration("test-app-client-id", "test-app-realm", "test-app", "apiKey", null, null, null, null);
	}
 
	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl");
	}*/
}