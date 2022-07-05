package com.ctc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author ankit pathak
 *
 */
@Configuration
@EnableSwagger2
public class OtherConfig {

	@Bean
	public Docket configureSwagger() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/claim/**"))
				.apis(RequestHandlerSelectors.basePackage("com.ctc.controller")).build().apiInfo(apiInfo());

	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Member Microservice Api", "Sample API for Claim", "1.0", "path/to/terms",
				new Contact("Member", "http://www.google.com", "ClaimAPI@gmail.com"), "API License",
				"http://www.google.com", Collections.emptyList());
	}

}
