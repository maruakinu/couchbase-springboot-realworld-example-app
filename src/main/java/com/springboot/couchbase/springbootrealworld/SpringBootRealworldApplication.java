package com.springboot.couchbase.springbootrealworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.service.ApiInfo;

@SpringBootApplication
public class SpringBootRealworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRealworldApplication.class, args);
	}



}
