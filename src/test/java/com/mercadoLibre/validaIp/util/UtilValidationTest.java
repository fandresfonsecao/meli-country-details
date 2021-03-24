package com.mercadoLibre.validaIp.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;

class UtilValidationTest {

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testNullIp() {
		Exception exception = assertThrows(RestException.class, () -> {
			UtilValidation.validateIp(null);
		}, "Not valid ip");
		assertTrue(
				exception.getMessage()
						.contains(ValidationException.NOT_VALID_IP.getMessage(anyString())),
				"Different message in exception throwed");
	}

	@Test
	void testNotValidIpV4() {
		String ip = "192.168.2";
		Exception exception = assertThrows(RestException.class, () -> {
			UtilValidation.validateIp(ip);
		}, "Not valid ip");
		assertTrue(exception.getMessage().contains(ValidationException.NOT_VALID_IP.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testValidIpV4() {
		String ip = "1.1.1.1";
		UtilValidation.validateIp(ip);
	}

	@Test
	void testNotValidIpV6() {
		String ip = "12001:0db8:85a30000:8a2e:0370:7334";
		Exception exception = assertThrows(RestException.class, () -> {
			UtilValidation.validateIp(ip);
		}, "Not valid ip");
		assertTrue(exception.getMessage().contains(ValidationException.NOT_VALID_IP.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testValidIpV6() {
		String ip = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
		UtilValidation.validateIp(ip);
	}

	@Test
	void testNotValidUrl() {
		String url = "1.1.1.1/resource.com";
		Exception exception = assertThrows(RestException.class, () -> {
			UtilValidation.validateUrl(url);
		}, "Not valid ip");
		assertTrue(
				exception.getMessage().contains(ValidationException.NOT_VALID_URL.getMessage(url)),
				"Different message in exception throwed");
	}

	@Test
	void testNullUrl() {
		String url = null;
		Exception exception = assertThrows(RestException.class, () -> {
			UtilValidation.validateUrl(url);
		}, "Not valid ip");
		assertTrue(
				exception.getMessage().contains(ValidationException.NOT_VALID_URL.getMessage(url)),
				"Different message in exception throwed");
	}

	@Test
	void testValidUrl() {
		String url = "https://www.google.com";
		UtilValidation.validateUrl(url);
	}

}
