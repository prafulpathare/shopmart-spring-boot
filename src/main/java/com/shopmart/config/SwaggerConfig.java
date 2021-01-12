package com.shopmart.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${spring.admin.email}")
	private String adminEmail;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.shopmart.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo()).securityContexts(Arrays.asList(this.securityContext()))
				.securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Shopmart REST API").description("Shopmart API documentation.")
				.termsOfServiceUrl("").contact(new Contact("Admin", "", adminEmail))
				.license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("1.0").build();
	}

	private ApiKey apiKey() {
		return new ApiKey("Bearer", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
	}

}