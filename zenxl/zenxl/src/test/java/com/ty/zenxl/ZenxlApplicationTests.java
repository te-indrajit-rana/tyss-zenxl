package com.ty.zenxl;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ZenxlApplicationTests {

	@Test
	@DisplayName("Application context loaded successfully.")
	void contextLoads(ApplicationContext context) {
		assertThat(context).isNotNull();
	}

}
