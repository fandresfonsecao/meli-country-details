package com.mercadoLibre.validaIp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercadoLibre.validaIp.ValidadorDeIpApplication;
import com.mercadoLibre.validaIp.dto.CountryDetailDto;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.NotFoundException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;
import com.mercadoLibre.validaIp.externalRestclient.CountryCurrencyClient;
import com.mercadoLibre.validaIp.externalRestclient.CountryExchangeClient;
import com.mercadoLibre.validaIp.externalRestclient.CountryInfoClient;
import com.mercadoLibre.validaIp.impl.BannedIpImpl;
import com.mercadoLibre.validaIp.impl.CountryDetailImpl;
import com.mercadoLibre.validaIp.persistence.CountryDetailFacade;
import com.mercadoLibre.validaIp.util.ApplicationProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ValidadorDeIpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
class CountryDetailImplIntegrationTest {

	@Autowired
	private CountryInfoClient countryInfoClient;

	@Autowired
	private CountryCurrencyClient countryCurrencyClient;

	@Autowired
	private CountryExchangeClient countryExchangeClient;

	@Autowired
	private BannedIpImpl bannedIpImpl;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private CountryDetailFacade countryDetailFacade;

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private CountryDetailImpl countryDetailImpl;

	@Test
	void testAllIsUp() {
		assertNotNull(countryInfoClient, "countryInfo is null");
		assertNotNull(countryCurrencyClient, "countryCurrency is null");
		assertNotNull(countryExchangeClient, "countryExchange is null");
		assertNotNull(bannedIpImpl, "bannedIpImpl is null");
		assertNotNull(cacheManager, "cacheManager is null");
		assertNotNull(countryDetailFacade, "countryDetailFacade is null");
		assertNotNull(applicationProperties, "applicationProperties is null");
	}

	@Test
	void testGetCountryDetailInvalidIp() {
		String ip = "4.4.4";
		Exception exception = assertThrows(RestException.class, () -> {
			countryDetailImpl.getCountryDetail(ip, new String[] { "EUR", "USD" });
		}, "Invalid Ip");
		assertTrue(exception.getMessage().contains(ValidationException.NOT_VALID_IP.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testGetCountryDetailBannedIp() {
		String ip = "4.4.4.4";
		bannedIpImpl.add(ip);
		Exception exception = assertThrows(RestException.class, () -> {
			countryDetailImpl.getCountryDetail(ip, new String[] { "EUR", "USD" });
		}, "Banned IP");
		assertTrue(exception.getMessage().contains(ValidationException.IP_BANNED.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testGetCountryDetailNonExistentIp() {
		String ip = "10.10.10.10";
		if (bannedIpImpl.exists(ip)) {
			bannedIpImpl.delete(ip);
		}
		Exception exception = assertThrows(RestException.class, () -> {
			countryDetailImpl.getCountryDetail(ip, new String[] { "EUR", "USD" });
		}, "Non existent contry");
		assertTrue(
				exception.getMessage().contains(NotFoundException.COUNTRY_NOT_FOUND.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testGetCountryDetailColombianIp() {
		String ip = "190.157.8.108";
		if (bannedIpImpl.exists(ip)) {
			bannedIpImpl.delete(ip);
		}
		CountryDetailDto countryDetailDto = countryDetailImpl.getCountryDetail(ip,
				new String[] { "EUR", "USD" });
		assertNotNull(countryDetailDto, "Country detail empty");
		assertNotNull(countryDetailDto.getName(), "Country name empty");
		assertNotNull(countryDetailDto.getIsoCode(), "ISO code empty");
		assertNotNull(countryDetailDto.getDmlTmst(), "Dml tmst empty");
		assertEquals("Colombia", countryDetailDto.getName(), "No match country name");
		assertEquals("COL", countryDetailDto.getIsoCode(), "No match ISO code name");
		assertEquals("COP", countryDetailDto.getCurrency().getCode(), "No match currency code");
		assertEquals("Colombian peso", countryDetailDto.getCurrency().getName(),
				"No match currency name");
	}

	@Test
	void testGetCountryDetailUsaIp() {
		String ip = "4.4.4.4";
		if (bannedIpImpl.exists(ip)) {
			bannedIpImpl.delete(ip);
		}
		CountryDetailDto countryDetailDto = countryDetailImpl.getCountryDetail(ip,
				new String[] { "EUR", "USD" });
		assertNotNull(countryDetailDto, "Country detail empty");
		assertNotNull(countryDetailDto.getName(), "Country name empty");
		assertNotNull(countryDetailDto.getIsoCode(), "ISO code empty");
		assertNotNull(countryDetailDto.getDmlTmst(), "Dml tmst empty");
		assertEquals("United States", countryDetailDto.getName(), "No match country name");
		assertEquals("USA", countryDetailDto.getIsoCode(), "No match ISO code name");
		assertEquals("USD", countryDetailDto.getCurrency().getCode(), "No match currency code");
		assertEquals("United States Dollar", countryDetailDto.getCurrency().getName(),
				"No match currency name");
	}

	@Test
	void testGetCountryDetailFranceIp() {
		String ip = "2.2.2.2";
		if (bannedIpImpl.exists(ip)) {
			bannedIpImpl.delete(ip);
		}
		CountryDetailDto countryDetailDto = countryDetailImpl.getCountryDetail(ip,
				new String[] { "EUR", "USD" });
		assertNotNull(countryDetailDto, "Country detail empty");
		assertNotNull(countryDetailDto.getName(), "Country name empty");
		assertNotNull(countryDetailDto.getIsoCode(), "ISO code empty");
		assertNotNull(countryDetailDto.getDmlTmst(), "Dml tmst empty");
		assertNotNull(countryDetailDto.getCurrency(), "Country currency empty");
		assertEquals("France", countryDetailDto.getName(), "No match country name");
		assertEquals("FRA", countryDetailDto.getIsoCode(), "No match ISO code name");
		assertEquals("EUR", countryDetailDto.getCurrency().getCode(), "No match currency code");
		assertEquals("Euro", countryDetailDto.getCurrency().getName(), "No match currency name");
	}

}
