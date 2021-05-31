package com.danipl.st.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket petApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-template")
                .apiInfo(getApiInfo())
                .select()
                .paths(petApiPaths())
                .build();
    }

    private Predicate<String> petApiPaths() {

        return regex(".*")
                .and(regex("/error/*").negate());
    }

    private ApiInfo getApiInfo() {

        return new ApiInfoBuilder()
                .title("API template tittle")
                .description("Api description")
                .contact(new Contact("Daniel", "", ""))
                .license("Apache License Version 2.0")
                .version("2.0")
                .build();
    }

}
