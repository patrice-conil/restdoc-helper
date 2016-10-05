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
package com.pconil.restdoc;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot Application class.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.pconil.restdoc")
@EnableSwagger2
public class SpringbootSampleApplication {

    /**
     * Main for app.
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(SpringbootSampleApplication.class)
                .bannerMode(Banner.Mode.LOG)
                .run(args);
    }
}
