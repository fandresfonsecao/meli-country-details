package com.mercadoLibre.validaIp.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.mercadoLibre.validaIp.dto.CountryDetailDto;
import com.mercadoLibre.validaIp.dto.CountryInfoResp;
import com.mercadoLibre.validaIp.dto.Currency;
import com.mercadoLibre.validaIp.dto.CurrencyExchangeDto;
import com.mercadoLibre.validaIp.dto.RateResp;
import com.mercadoLibre.validaIp.externalRestclient.CountryCurrencyClient;
import com.mercadoLibre.validaIp.externalRestclient.CountryExchangeClient;
import com.mercadoLibre.validaIp.externalRestclient.CountryInfoClient;
import com.mercadoLibre.validaIp.persistence.CountryDetailFacade;
import com.mercadoLibre.validaIp.persistence.entity.CountryDetail;
import com.mercadoLibre.validaIp.util.ApplicationProperties;
import com.mercadoLibre.validaIp.util.ApplicationPropertyCode;
import com.mercadoLibre.validaIp.util.CountryDetailTransformer;
import com.mercadoLibre.validaIp.util.UtilValidation;

@Service
public class CountryDetailImpl extends CustomCache<CountryDetailDto> {

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

	/**
	 * Call external services to get details like name, currency and exchange rates.
	 * 
	 * @param ip
	 *            to get country details
	 * @return {@link com.mercadoLibre.validaIp.dto.CountryDetailDto}
	 */
	public CountryDetailDto getCountryDetail(String ip, String[] filterCurrencyCodes) {
		UtilValidation.validateIp(ip);
		bannedIpImpl.checkBannedIp(ip);
		CountryInfoResp countryInfoResp = countryInfoClient.getCountryByIp(ip);
		CountryDetailDto countryDetailDto = super.getFromCache(
				CountryDetailTransformer.toCountryDetailDto(countryInfoResp.getCountryCode3()));
		boolean isValidCache = isValidCache(countryDetailDto, filterCurrencyCodes);
		if (BooleanUtils.isFalse(isValidCache)) {
			countryDetailDto = new CountryDetailDto();
			countryDetailDto.setName(countryInfoResp.getCountryName());
			countryDetailDto.setIsoCode(countryInfoResp.getCountryCode3());
			Currency currency = countryCurrencyClient.getCurency(countryInfoResp.getCountryName());
			countryDetailDto.setCurrency(currency);
			RateResp rateResp = countryExchangeClient
					.getExchangeRate(countryDetailDto.getCurrency().getCode(), filterCurrencyCodes);
			ArrayList<CurrencyExchangeDto> currencyExchanges = new ArrayList<CurrencyExchangeDto>();
			Iterator<String> iterator = rateResp.getRates().keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				CurrencyExchangeDto currencyExchange = new CurrencyExchangeDto();
				currencyExchange.setType(key);
				currencyExchange.setValue(rateResp.getRates().get(key));
				currencyExchanges.add(currencyExchange);
			}
			countryDetailDto.setCurrencyExchanges(currencyExchanges);
			CountryDetail countryDetail = CountryDetailTransformer
					.toCountryDetail(countryDetailDto);
			countryDetailFacade.save(countryDetail);
			countryDetailDto = CountryDetailTransformer.toCountryDetailDto(countryDetail);
			super.addToCache(countryDetailDto);
		}
		if (Objects.nonNull(countryDetailDto)
				&& CollectionUtils.isNotEmpty(countryDetailDto.getCurrencyExchanges())
				&& ArrayUtils.isNotEmpty(filterCurrencyCodes)) {
			countryDetailDto.getCurrencyExchanges()
					.removeIf(item -> !ArrayUtils.contains(filterCurrencyCodes, item.getType()));
		}
		return countryDetailDto;
	}

	private boolean isValidCache(CountryDetailDto countryDetailDto, String[] filterCurrencyCodes) {
		boolean isValidCache = false;
		if (Objects.nonNull(countryDetailDto)) {
			long secondsLapse = new Date().getTime() - countryDetailDto.getDmlTmst().getTime();
			Integer cacheTimeSeconds = new Integer(
					applicationProperties.getProperty(ApplicationPropertyCode.CACHE_IN_SECONDS));
			if (TimeUnit.SECONDS.convert(secondsLapse, TimeUnit.MILLISECONDS) <= cacheTimeSeconds) {
				isValidCache = true;
				if (CollectionUtils.isNotEmpty(countryDetailDto.getCurrencyExchanges())
						&& ArrayUtils.isNotEmpty(filterCurrencyCodes)) {
					int counter = 0;
					for (CurrencyExchangeDto currencyExchangeDto : countryDetailDto
							.getCurrencyExchanges()) {
						if (ArrayUtils.contains(filterCurrencyCodes,
								currencyExchangeDto.getType())) {
							counter++;
						}
					}
					isValidCache = countryDetailDto.getCurrencyExchanges().size() == counter;
				}
			}
			if (BooleanUtils.isFalse(isValidCache)) {
				super.removeFromCache(countryDetailDto);
			}
		}
		return isValidCache;
	}

	@Override
	protected String getCacheName() {
		return CountryDetailDto.class.getSimpleName();
	}

	@Override
	protected CacheManager getCacheManager() {
		return cacheManager;
	}

}
