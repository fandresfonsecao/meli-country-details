package com.mercadoLibre.validaIp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
class ValidadorDeIpApplicationTests {

	@Test
	void contextLoads() {
	}

}
