package com.ekichabi_business_registration;

import com.ekichabi_business_registration.db.repository.BusinessRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    // Main app to run
    public static void main(final String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    static ApplicationRunner init(BusinessRepository businessRepository) {
        return args -> System.out.println("number of businesses: " + businessRepository.count());
    }
}
