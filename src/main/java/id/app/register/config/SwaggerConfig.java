package id.app.register.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
//import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
	       return new Docket(DocumentationType.SWAGGER_2)
	    	   .select()
	          .apis(RequestHandlerSelectors.basePackage("id.app.register.controller"))
	       	  .paths(regex("/account.*"))
	       	  .build()
	       	  .securitySchemes(Arrays.asList(basicAuthScheme()))
	       	  .apiInfo(apiInfo());
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("APP Register Account")
				.description("API R A")
				.version("1.0")
				.build();
	}
	
	@Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(-1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

	private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    
}