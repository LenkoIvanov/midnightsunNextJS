package com.midnightsun.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("test")
@AutoConfigureMockMvc
@SpringBootTest
class ProductServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
