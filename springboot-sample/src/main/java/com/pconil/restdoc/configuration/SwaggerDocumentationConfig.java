/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pconil.restdoc.configuration;

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
 *
 * @author  patrice_conil
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
                .apis(RequestHandlerSelectors.basePackage("com.pconil.restdoc"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

}
