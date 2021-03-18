package com.mercadoLibre.validaIp.externalRestclient;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.mercadoLibre.validaIp.dto.RateResp;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;
import com.mercadoLibre.validaIp.util.ApplicationProperties;
import com.mercadoLibre.validaIp.util.ApplicationPropertyCode;

/**
 * Call external service and get country's exchanges to different currencies
 * 
 * @author fabian.fonseca
 *
 */
@Repository
public class CountryExchangeClient {

	@Autowired
	private ApplicationProperties applicationProperties;

	/**
	 * Get exchange to different currencies
	 * 
	 * @param countryCurrencyCode
	 *            base currency code in other words country's currency code
	 * @return
	 */
	public RateResp getExchangeRate(String countryCurrencyCode, String[] currencyCodes) {
		String baseEndpoint = applicationProperties
				.getProperty(ApplicationPropertyCode.COUNTRY_CURRENCY_ENDPOINT);
		currencyCodes = ArrayUtils.isEmpty(currencyCodes) ? new String[] { "EUR", "USD" }
				: currencyCodes;
		String endpoint = MessageFormat.format(baseEndpoint, countryCurrencyCode,
				String.join(",", currencyCodes));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<RateResp> responseEntity = restTemplate.getForEntity(endpoint,
				RateResp.class);
		RateResp rateResp = responseEntity.getBody();
		if (BooleanUtils.isFalse(rateResp.isSuccess())) {
			throw new RestException(ValidationException.EXCHANGE_SERVICE_FAIL, countryCurrencyCode,
					rateResp.getRateError().getCode().toString(),
					rateResp.getRateError().getType());
		}
		return rateResp;
	}

}
