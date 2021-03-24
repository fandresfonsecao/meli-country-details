package com.mercadoLibre.validaIp.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.mercadoLibre.validaIp.dto.CountryDetailDto;
import com.mercadoLibre.validaIp.dto.CountryInfoResp;
import com.mercadoLibre.validaIp.dto.Currency;
import com.mercadoLibre.validaIp.dto.CurrencyExchangeDto;
import com.mercadoLibre.validaIp.externalRestclient.CountryCurrencyClient;
import com.mercadoLibre.validaIp.externalRestclient.CountryExchangeClient;
import com.mercadoLibre.validaIp.externalRestclient.CountryInfoClient;
import com.mercadoLibre.validaIp.persistence.BanIpFacade;
import com.mercadoLibre.validaIp.persistence.CountryDetailFacade;
import com.mercadoLibre.validaIp.util.ApplicationProperties;
import com.mercadoLibre.validaIp.util.ApplicationPropertyCode;
import com.mercadoLibre.validaIp.util.UtilDatetime;

class CountryDetailImplTest {

	@Mock
	private Cache cache;

	@Mock
	private CountryInfoClient countryInfoClient;

	@Mock
	private CountryCurrencyClient countryCurrencyClient;

	@Mock
	private CountryExchangeClient countryExchangeClient;

	@Mock
	private BannedIpImpl bannedIpImpl;

	@Mock
	private CacheManager cacheManager;

	@Mock
	private CountryDetailFacade countryDetailFacade;

	@Mock
	private ApplicationProperties applicationProperties;

	@Mock
	private BanIpFacade banIpFacade;

	@InjectMocks
	private CountryDetailImpl countryDetailImpl;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Cache cache = Mockito.mock(Cache.class);
		when(cacheManager.getCache(countryDetailImpl.getCacheName())).thenReturn(cache);
	}

	@Test
	void testGetCountryDetailFromCache() {
		String ip = "1.1.1.1";
		CountryInfoResp countryInfoResp = new CountryInfoResp();
		countryInfoResp.setCountryCode("CO");
		countryInfoResp.setCountryCode3("COL");
		countryInfoResp.setCountryName("Colombia");
		when(countryInfoClient.getCountryByIp(ip)).thenReturn(countryInfoResp);
		CountryDetailDto countryDetailDtoCache = new CountryDetailDto();
		countryDetailDtoCache.setName(countryInfoResp.getCountryName());
		countryDetailDtoCache.setIsoCode(countryInfoResp.getCountryCode3());
		countryDetailDtoCache.setDmlTmst(UtilDatetime.getTimestampNow());
		Currency currency = new Currency();
		currency.setCode("COP");
		currency.setName("Colombian peso");
		currency.setSymbol("$");
		countryDetailDtoCache.setCurrency(currency);
		List<CurrencyExchangeDto> currencyExchanges = new ArrayList<>();
		CurrencyExchangeDto currencyExchangeDto = new CurrencyExchangeDto();
		currencyExchangeDto.setType("USD");
		currencyExchangeDto.setValue(3561.25);
		currencyExchanges.add(currencyExchangeDto);
		countryDetailDtoCache.setCurrencyExchanges(currencyExchanges);
		when(cache.get(countryDetailDtoCache.getId(), CountryDetailDto.class))
				.thenReturn(countryDetailDtoCache);
		when(countryDetailImpl.getFromCache(countryDetailDtoCache))
				.thenReturn(countryDetailDtoCache);
		when(applicationProperties.getProperty(ApplicationPropertyCode.CACHE_IN_SECONDS))
				.thenReturn("60");
		CountryDetailDto countryDetailDto = countryDetailImpl.getCountryDetail(ip, null);
		assertNotNull(countryDetailDto, "Country detail null");
		assertNotNull(countryDetailDto.getName(), "Country name empty");
		assertNotNull(countryDetailDto.getIsoCode(), "ISO code empty");
		assertNotNull(countryDetailDto.getDmlTmst(), "Dml tmst empty");
		assertEquals("Colombia", countryDetailDto.getName(), "No match country name");
		assertEquals("COL", countryDetailDto.getIsoCode(), "No match ISO code name");
		assertEquals("COP", countryDetailDto.getCurrency().getCode(), "No match currency code");
		assertEquals("Colombian peso", countryDetailDto.getCurrency().getName(),
				"No match currency name");
		assertTrue(CollectionUtils.isNotEmpty(countryDetailDto.getCurrencyExchanges()),
				"Currency exchanges is empty");
	}

	@Test
	void testGetCountryDetailNoCache() {
		String ip = "1.1.1.1";
		CountryInfoResp countryInfoResp = new CountryInfoResp();
		countryInfoResp.setCountryCode("CO");
		countryInfoResp.setCountryCode3("COL");
		countryInfoResp.setCountryName("Colombia");
		when(countryInfoClient.getCountryByIp(ip)).thenReturn(countryInfoResp);
		CountryDetailDto countryDetailDtoCache = new CountryDetailDto();
		countryDetailDtoCache.setName(countryInfoResp.getCountryName());
		countryDetailDtoCache.setIsoCode(countryInfoResp.getCountryCode3());
		countryDetailDtoCache.setDmlTmst(UtilDatetime.getTimestampNow());
		Currency currency = new Currency();
		currency.setCode("COP");
		currency.setName("Colombian peso");
		currency.setSymbol("$");
		countryDetailDtoCache.setCurrency(currency);
		List<CurrencyExchangeDto> currencyExchanges = new ArrayList<>();
		CurrencyExchangeDto currencyExchangeDto = new CurrencyExchangeDto();
		currencyExchangeDto.setType("USD");
		currencyExchangeDto.setValue(3561.25);
		currencyExchanges.add(currencyExchangeDto);
		countryDetailDtoCache.setCurrencyExchanges(currencyExchanges);
		when(countryDetailImpl.getFromCache(countryDetailDtoCache)).thenReturn(null);
		when(applicationProperties.getProperty(ApplicationPropertyCode.CACHE_IN_SECONDS))
				.thenReturn("60");
		when(countryCurrencyClient.getCurency(countryInfoResp.getCountryName()))
				.thenReturn(currency);
		when(countryExchangeClient.getCurrencyExchanges(currency.getCode(), null))
				.thenReturn(currencyExchanges);
		CountryDetailDto countryDetailDto = countryDetailImpl.getCountryDetail(ip, null);
		assertNotNull(countryDetailDto, "Country detail null");
		assertNotNull(countryDetailDto.getName(), "Country name empty");
		assertNotNull(countryDetailDto.getIsoCode(), "ISO code empty");
		assertNotNull(countryDetailDto.getDmlTmst(), "Dml tmst empty");
		assertEquals("Colombia", countryDetailDto.getName(), "No match country name");
		assertEquals("COL", countryDetailDto.getIsoCode(), "No match ISO code name");
		assertEquals("COP", countryDetailDto.getCurrency().getCode(), "No match currency code");
		assertEquals("Colombian peso", countryDetailDto.getCurrency().getName(),
				"No match currency name");
		assertTrue(CollectionUtils.isNotEmpty(countryDetailDto.getCurrencyExchanges()),
				"Currency exchanges is empty");
	}

}