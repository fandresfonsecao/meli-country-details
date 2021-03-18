package com.mercadoLibre.validaIp.externalRestclient;

import java.text.MessageFormat;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.mercadoLibre.validaIp.dto.CountryInfoResp;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.NotFoundException;
import com.mercadoLibre.validaIp.util.ApplicationProperties;
import com.mercadoLibre.validaIp.util.ApplicationPropertyCode;

/**
 * Call external service and get country's information
 * 
 * @author fabian.fonseca
 *
 */
@Repository
public class CountryInfoClient {

	@Autowired
	private ApplicationProperties applicationProperties;

	/**
	 * Get country's basic information given an IP
	 * 
	 * @param ip
	 *            to query basic country information
	 * @return {@link com.mercadoLibre.validaIp.dto.CountryInfoResp}
	 */
	public CountryInfoResp getCountryByIp(String ip) {
		String baseEndpoint = applicationProperties
				.getProperty(ApplicationPropertyCode.COUNTRY_INFO_ENDPOINT);
		String endpoint = MessageFormat.format(baseEndpoint, ip);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CountryInfoResp> responseEntity = restTemplate.getForEntity(endpoint,
				CountryInfoResp.class);
		CountryInfoResp countryInfoResp = responseEntity.getBody();
		if (Objects.isNull(countryInfoResp)
				|| StringUtils.isBlank(countryInfoResp.getCountryName())) {
			throw new RestException(NotFoundException.COUNTRY_NOT_FOUND, ip);
		}
		return countryInfoResp;
	}

}
