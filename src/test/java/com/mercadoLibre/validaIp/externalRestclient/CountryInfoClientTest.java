package com.mercadoLibre.validaIp.externalRestclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mercadoLibre.validaIp.dto.CountryInfoResp;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.NotFoundException;
import com.mercadoLibre.validaIp.util.ApplicationProperties;
import com.mercadoLibre.validaIp.util.ApplicationPropertyCode;

class CountryInfoClientTest {

	@Mock
	private ApplicationProperties applicationProperties;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CountryInfoClient countryInfoClient;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCountryInfoClientCountryNotFound() {
		String ip = "1.1.1.1";
		String endpoint = "https://api.ip2country.info/ip?{0}";
		when(applicationProperties.getProperty(ApplicationPropertyCode.COUNTRY_INFO_ENDPOINT))
				.thenReturn(endpoint);
		CountryInfoResp countryInfoResp = null;
		when(restTemplate.getForEntity(MessageFormat.format(endpoint, ip), CountryInfoResp.class))
				.thenReturn(new ResponseEntity(countryInfoResp, HttpStatus.OK));
		Exception exception = assertThrows(RestException.class, () -> {
			countryInfoClient.getCountryByIp(ip);
		}, "Non existent contry");
		assertTrue(
				exception.getMessage().contains(NotFoundException.COUNTRY_NOT_FOUND.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testCountryInfo() {
		String ip = "1.1.1.1";
		String endpoint = "https://api.ip2country.info/ip?{0}";
		when(applicationProperties.getProperty(ApplicationPropertyCode.COUNTRY_INFO_ENDPOINT))
				.thenReturn(endpoint);
		CountryInfoResp countryInfoResp = new CountryInfoResp();
		countryInfoResp.setCountryCode("CO");
		countryInfoResp.setCountryCode3("COL");
		countryInfoResp.setCountryName("Colombia");
		when(restTemplate.getForEntity(MessageFormat.format(endpoint, ip), CountryInfoResp.class))
				.thenReturn(new ResponseEntity(countryInfoResp, HttpStatus.OK));
		CountryInfoResp countryInfoResponse = countryInfoClient.getCountryByIp(ip);
		assertTrue(Objects.nonNull(countryInfoResponse));
		assertTrue(StringUtils.isNotBlank(countryInfoResponse.getCountryCode()));
		assertTrue(StringUtils.isNotBlank(countryInfoResponse.getCountryCode3()));
		assertTrue(StringUtils.isNotBlank(countryInfoResponse.getCountryName()));
		assertEquals(countryInfoResp.getCountryCode(), countryInfoResponse.getCountryCode());
		assertEquals(countryInfoResp.getCountryCode3(), countryInfoResponse.getCountryCode3());
		assertEquals(countryInfoResp.getCountryName(), countryInfoResponse.getCountryName());
	}

}
