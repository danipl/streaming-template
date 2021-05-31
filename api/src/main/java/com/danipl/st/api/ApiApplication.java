package com.danipl.st.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.danipl.st.api.ApiApplication.PACKAGE_TO_SCAN;

@SpringBootApplication(scanBasePackages = PACKAGE_TO_SCAN)
@EnableJpaRepositories(basePackages = PACKAGE_TO_SCAN)
@EntityScan(value = PACKAGE_TO_SCAN)
public class ApiApplication {

    static final String PACKAGE_TO_SCAN = "com.danipl.st";

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
