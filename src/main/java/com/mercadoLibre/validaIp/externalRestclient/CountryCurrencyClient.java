package com.mercadoLibre.validaIp.externalRestclient;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.mercadoLibre.validaIp.dto.Currency;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.NotFoundException;
import com.mercadoLibre.validaIp.util.ApplicationProperties;
import com.mercadoLibre.validaIp.util.ApplicationPropertyCode;

/**
 * Client to external service
 * 
 * @author fabian.fonseca
 *
 */
@Repository
public class CountryCurrencyClient {

	@Autowired
	private ApplicationProperties applicationProperties;

	/**
	 * Get the currency for any given country name
	 * 
	 * @param countryName
	 * @return {@link com.mercadoLibre.validaIp.dto.Currency}
	 */
	public Currency getCurency(String countryName) {
		String baseEndpoint = applicationProperties
				.getProperty(ApplicationPropertyCode.COUNTRY_DETAIL_ENDPOINT);
		String endpoint = MessageFormat.format(baseEndpoint, countryName);
		RestTemplate restTemplate = new RestTemplate();
		// Strange response conventional way for getting it didn't work
		Object[] responseObject = restTemplate.getForObject(endpoint, Object[].class);
		if (Objects.isNull(responseObject)) {
			throw new RestException(NotFoundException.CURRENCIES_NOT_FOUND, countryName);
		}
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> responseLinkedHashMap = (LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>>) responseObject[0];
		if (Objects.isNull(responseLinkedHashMap)) {
			throw new RestException(NotFoundException.CURRENCIES_NOT_FOUND, countryName);
		}
		ArrayList<LinkedHashMap<String, String>> responseArrayList = responseLinkedHashMap
				.get("currencies");
		if (CollectionUtils.isEmpty(responseArrayList)) {
			throw new RestException(NotFoundException.CURRENCIES_NOT_FOUND, countryName);
		}
		LinkedHashMap<String, String> responseLinkedHashMapInner = responseArrayList.get(0);
		Currency currency = new Currency();
		currency.setCode(responseLinkedHashMapInner.get("code"));
		currency.setName(responseLinkedHashMapInner.get("name"));
		currency.setSymbol(responseLinkedHashMapInner.get("symbol"));
		return currency;
	}

}