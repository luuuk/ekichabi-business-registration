package com.ekichabi_business_registration.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class AppTests {

	@Test
    @Transactional
	void contextLoads() {
	}

}
