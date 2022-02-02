package com.ekichabi_business_registration;

import com.ekichabi_business_registration.db.repository.BusinessRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    // Main app to run
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

    @Bean
    ApplicationRunner init(BusinessRepository businessRepository) {
//	    TODO: This should be a transaction

        return args -> businessRepository.findAll().forEach(System.out::println);
    }
}
