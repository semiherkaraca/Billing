package com.billing.config;


import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customImplementation() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Smart Billing")
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.billing.controller"))
                .build()
                .apiInfo(apiInfo())
                //.securitySchemes(Collections.singletonList(apiKey()))
                //.securityContexts(Lists.newArrayList(securityContext()))
                .globalOperationParameters(getGlobalParameters());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API Documentation of Smart Billing")
                .version("1.0.0")
                .description("REST API for Smart Billing")
                .contact(new Contact("Smart Billing", "Contact_URL", "contact@email.com"))
                .build();
    }

    private List<Parameter> getGlobalParameters() {
        return Lists.newArrayList(createParameter("deviceId", "1233456"));
    }

    private Parameter createParameter(String name, String defaultValue) {
        return new ParameterBuilder().name(name)
                .required(true)
                .allowEmptyValue(false)
                .allowMultiple(false)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue(defaultValue)
                .build();
    }
/*
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().
                securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }*/


}
