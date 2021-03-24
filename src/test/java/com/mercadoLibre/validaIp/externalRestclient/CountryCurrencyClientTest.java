package com.mercadoLibre.validaIp.externalRestclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mercadoLibre.validaIp.dto.Currency;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.NotFoundException;
import com.mercadoLibre.validaIp.util.ApplicationProperties;
import com.mercadoLibre.validaIp.util.ApplicationPropertyCode;

class CountryCurrencyClientTest {

	@Mock
	private ApplicationProperties applicationProperties;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CountryCurrencyClient countryCurrencyClient;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCurrencyNotFound() {
		String countryCode = "COL";
		String endpoint = "https://restcountries.eu/rest/v2/name/{0}?fields=currencies";
		when(applicationProperties.getProperty(ApplicationPropertyCode.COUNTRY_DETAIL_ENDPOINT))
				.thenReturn(endpoint);
		Object[] responseObject = null;
		when(restTemplate.getForEntity(MessageFormat.format(endpoint, countryCode), Object[].class))
				.thenReturn(new ResponseEntity(responseObject, HttpStatus.OK));
		Exception exception = assertThrows(RestException.class,
				() -> countryCurrencyClient.getCurency(countryCode), "Country without currency");
		assertTrue(
				exception.getMessage()
						.contains(NotFoundException.CURRENCIES_NOT_FOUND.getMessage(countryCode)),
				"Different message in exception throwed");
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> responseLinkedHashMap = null;
		responseObject = new Object[] { responseLinkedHashMap };
		when(restTemplate.getForObject(MessageFormat.format(endpoint, countryCode), Object[].class))
				.thenReturn(responseObject);
		exception = assertThrows(RestException.class,
				() -> countryCurrencyClient.getCurency(countryCode), "Country without currency");
		assertTrue(
				exception.getMessage()
						.contains(NotFoundException.CURRENCIES_NOT_FOUND.getMessage(countryCode)),
				"Different message in exception throwed");
		responseLinkedHashMap = new LinkedHashMap<>();
		ArrayList<LinkedHashMap<String, String>> reponseArrayList = new ArrayList<LinkedHashMap<String, String>>();
		responseLinkedHashMap.put("currencies", reponseArrayList);
		responseObject = new Object[] { responseLinkedHashMap };
		when(restTemplate.getForObject(MessageFormat.format(endpoint, countryCode), Object[].class))
				.thenReturn(responseObject);
		exception = assertThrows(RestException.class,
				() -> countryCurrencyClient.getCurency(countryCode), "Country without currency");
		assertTrue(
				exception.getMessage()
						.contains(NotFoundException.CURRENCIES_NOT_FOUND.getMessage(countryCode)),
				"Different message in exception throwed");
		LinkedHashMap<String, String> responseLinkedHashMapInner = new LinkedHashMap<>();
		responseLinkedHashMapInner.put("code", "");
		responseLinkedHashMapInner.put("name", "");
		responseLinkedHashMapInner.put("symbol", "");
		reponseArrayList.add(responseLinkedHashMapInner);
		responseLinkedHashMap = new LinkedHashMap<>();
		responseLinkedHashMap.clear();
		responseLinkedHashMap.put("currencies", reponseArrayList);
		responseObject = new Object[] { responseLinkedHashMap };
		when(restTemplate.getForObject(MessageFormat.format(endpoint, countryCode), Object[].class))
				.thenReturn(responseObject);
		exception = assertThrows(RestException.class,
				() -> countryCurrencyClient.getCurency(countryCode), "Country without currency");
		assertTrue(
				exception.getMessage()
						.contains(NotFoundException.CURRENCIES_NOT_FOUND.getMessage(countryCode)),
				"Different message in exception throwed");
	}

	@Test
	public void testCurrencyFound() {
		String countryCode = "COL";
		String endpoint = "https://restcountries.eu/rest/v2/name/{0}?fields=currencies";
		when(applicationProperties.getProperty(ApplicationPropertyCode.COUNTRY_DETAIL_ENDPOINT))
				.thenReturn(endpoint);
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> responseLinkedHashMap = null;
		responseLinkedHashMap = new LinkedHashMap<>();
		ArrayList<LinkedHashMap<String, String>> reponseArrayList = new ArrayList<LinkedHashMap<String, String>>();
		responseLinkedHashMap.put("currencies", reponseArrayList);
		LinkedHashMap<String, String> responseLinkedHashMapInner = new LinkedHashMap<>();
		responseLinkedHashMapInner.put("code", "COP");
		responseLinkedHashMapInner.put("name", "Colombian peso");
		responseLinkedHashMapInner.put("symbol", "$");
		reponseArrayList.add(responseLinkedHashMapInner);
		responseLinkedHashMap = new LinkedHashMap<>();
		responseLinkedHashMap.clear();
		responseLinkedHashMap.put("currencies", reponseArrayList);
		Object[] responseObject = new Object[] { responseLinkedHashMap };
		when(restTemplate.getForObject(MessageFormat.format(endpoint, countryCode), Object[].class))
				.thenReturn(responseObject);
		Currency currency = countryCurrencyClient.getCurency(countryCode);
		assertNotNull(currency, "Response object null");
		assertEquals("COP", currency.getCode(), "Currency code different thna expected");
		assertEquals("Colombian peso", currency.getName(), "Currency name different thna expected");
		assertEquals("$", currency.getSymbol(), "Currency symbol different thna expected");
	}

}
