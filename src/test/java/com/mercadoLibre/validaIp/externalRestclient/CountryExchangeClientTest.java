package com.mercadoLibre.validaIp.externalRestclient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mercadoLibre.validaIp.dto.CurrencyExchangeDto;
import com.mercadoLibre.validaIp.dto.RateError;
import com.mercadoLibre.validaIp.dto.RateResp;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;
import com.mercadoLibre.validaIp.util.ApplicationProperties;
import com.mercadoLibre.validaIp.util.ApplicationPropertyCode;

class CountryExchangeClientTest {

	@Mock
	private ApplicationProperties applicationProperties;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CountryExchangeClient countryExchangeClient;

	private String[] currencyCodes = new String[] { "EUR", "USD" };

	private String baseEndpoint = "http://data.fixer.io/api/latest?access_key=d419db1030226d184141f9ff6eaf2fd7&base={0}&symbols={1}";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(applicationProperties.getProperty(ApplicationPropertyCode.COUNTRY_CURRENCY_ENDPOINT))
				.thenReturn(baseEndpoint);
	}

	@Test
	public void testExternalServiceFail() {
		String baseCurrencyCode = "PPP";
		RateResp rateResp = new RateResp();
		RateError rateError = new RateError();
		rateError.setCode(201);
		rateError.setType("invalid_base_currency");
		rateResp.setRateError(rateError);
		rateResp.setSuccess(Boolean.FALSE);
		when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
				.thenReturn(new ResponseEntity(rateResp, HttpStatus.OK));
		Exception exception = assertThrows(RestException.class,
				() -> countryExchangeClient.getCurrencyExchanges(baseCurrencyCode, currencyCodes),
				"Country without currency");
		assertTrue(exception.getMessage()
				.contains(ValidationException.EXCHANGE_SERVICE_FAIL.getMessage(baseCurrencyCode,
						rateError.getCode().toString(), rateError.getType())),
				"Different message in exception throwed");
	}

	@Test
	public void testExternalServiceSucess() {
		String baseCurrencyCode = "PPP";
		RateResp rateResp = new RateResp();
		rateResp.setBase(baseCurrencyCode);
		rateResp.setDate(LocalDate.now());
		rateResp.setRateError(null);
		Map<String, Double> rates = new HashMap<>();
		rates.put("EUR", 0.000233);
		rates.put("USD", 0.000275);
		rateResp.setRates(rates);
		rateResp.setSuccess(Boolean.TRUE);
		rateResp.setTimestamp(new Date().getTime());
		when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
				.thenReturn(new ResponseEntity(rateResp, HttpStatus.OK));
		List<CurrencyExchangeDto> currencyExchangeDtos = countryExchangeClient
				.getCurrencyExchanges(baseCurrencyCode, currencyCodes);
		assertNotNull(currencyExchangeDtos);
		assertTrue(CollectionUtils.isNotEmpty(currencyExchangeDtos));

	}

}
