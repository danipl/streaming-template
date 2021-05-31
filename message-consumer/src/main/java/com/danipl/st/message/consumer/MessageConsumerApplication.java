package com.danipl.st.message.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.danipl.st")
@EnableJpaRepositories(basePackages = "com.danipl.st")
@EntityScan(value = "com.danipl.st")
public class MessageConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageConsumerApplication.class, args);
    }

}
