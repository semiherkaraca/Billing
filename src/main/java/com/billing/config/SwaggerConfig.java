package com.billing.config;


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
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API Documentation of Smart Billing")
                .version("1.0.0")
                .description("REST API for Smart Billing")
                .contact(new Contact("Smart Billing", "Contact_URL", "contact@email.com"))
                .build();
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
}
