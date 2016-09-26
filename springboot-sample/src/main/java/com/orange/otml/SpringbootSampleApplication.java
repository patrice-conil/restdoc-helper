package com.orange.otml;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot Application class.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.orange.otml")
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
