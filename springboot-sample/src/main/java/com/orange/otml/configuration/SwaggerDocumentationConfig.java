package com.orange.otml.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * Swagger doc.
 */
@Configuration
public class SwaggerDocumentationConfig {

    /**
     * Api info.
     *
     * @return Api info
     */
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sample API")
                .description("")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("1.0.0-SNAPSHOT")
                .contact(new Contact("Me", "", "me@myserver.com"))
                .build();
    }

    /**
     * Custom implementation.
     *
     * @return custom implementation doclet
     */
    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.orange.otml"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

}
