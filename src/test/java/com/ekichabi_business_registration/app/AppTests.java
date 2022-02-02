package com.ekichabi_business_registration.app;

import com.ekichabi_business_registration.app.common.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

@IntegrationTest
@Transactional
class AppTests {

    @BeforeEach
    void initData() {
    }

	@Test
    @Transactional
	void contextLoads() {
	}

}
