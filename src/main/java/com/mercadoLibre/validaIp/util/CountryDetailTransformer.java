package com.mercadoLibre.validaIp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;

import com.mercadoLibre.validaIp.dto.CountryDetailDto;
import com.mercadoLibre.validaIp.dto.Currency;
import com.mercadoLibre.validaIp.dto.CurrencyExchangeDto;
import com.mercadoLibre.validaIp.persistence.entity.CountryCurrency;
import com.mercadoLibre.validaIp.persistence.entity.CountryDetail;
import com.mercadoLibre.validaIp.persistence.entity.CurrencyExchange;

/**
 * Transform DTO to entity and backwards
 * 
 * @author fabian.fonseca
 *
 */
public class CountryDetailTransformer {

	/**
	 * Transform {@link com.mercadoLibre.validaIp.dto.CountryDetailDto} to
	 * {@link com.mercadoLibre.validaIp.persistence.entity.CountryDetail}
	 * 
	 * @param {@link
	 * 			com.mercadoLibre.validaIp.persistence.entity.CountryDetail} base
	 *            object for transformation
	 * @return {@link com.mercadoLibre.validaIp.dto.CountryDetailDto} transformed
	 *         Dto
	 */
	public static CountryDetailDto toCountryDetailDto(CountryDetail countryDetail) {
		CountryDetailDto countryDetailDto = new CountryDetailDto();
		countryDetailDto.setIsoCode(countryDetail.getCountryCode());
		countryDetailDto.setName(countryDetail.getCountryName());
		countryDetailDto.setDmlTmst(countryDetail.getDmlTmst());
		List<CountryCurrency> countryCurrencies = countryDetail.getCountryCurrencies();
		if (CollectionUtils.isNotEmpty(countryCurrencies)) {
			Currency currency = new Currency();
			currency.setCode(countryCurrencies.get(0).getCurrencyCode());
			currency.setName(countryCurrencies.get(0).getCurrencyName());
			currency.setSymbol(countryCurrencies.get(0).getCurrencySymbol());
			countryDetailDto.setCurrency(currency);
		}
		List<CurrencyExchange> currencyExchanges = countryDetail.getCurrencyExchanges();
		if (CollectionUtils.isNotEmpty(currencyExchanges)) {
			List<CurrencyExchangeDto> currencyExchangeDtos = new ArrayList<CurrencyExchangeDto>();
			for (CurrencyExchange currencyExchange : currencyExchanges) {
				CurrencyExchangeDto currencyExchangeDto = new CurrencyExchangeDto();
				currencyExchangeDto.setType(currencyExchange.getCurrencyCode());
				currencyExchangeDto.setValue(currencyExchange.getValue());
				currencyExchangeDtos.add(currencyExchangeDto);
			}
			countryDetailDto.setCurrencyExchanges(currencyExchangeDtos);
		}
		return countryDetailDto;
	}

	/**
	 * Creates {@link com.mercadoLibre.validaIp.dto.CountryDetailDto} only with
	 * countryCode
	 * 
	 * @param countryCode
	 *            to create the object
	 * @return {@link com.mercadoLibre.validaIp.dto.CountryDetailDto} with only
	 *         countryCode attribute
	 */
	public static CountryDetailDto toCountryDetailDto(String countryCode) {
		CountryDetailDto countryDetailResp = new CountryDetailDto();
		countryDetailResp.setIsoCode(countryCode);
		return countryDetailResp;
	}

	/**
	 * Transform {@link com.mercadoLibre.validaIp.dto.CountryDetailDto} to
	 * {@link com.mercadoLibre.validaIp.persistence.entity.CountryDetail}
	 * 
	 * @param {@link
	 * 			com.mercadoLibre.validaIp.dto.CountryDetailDto} base object for
	 *            transformation
	 * @return {@link com.mercadoLibre.validaIp.persistence.entity.CountryDetail}
	 *         transformed entity
	 */
	public static CountryDetail toCountryDetail(CountryDetailDto countryDetailDto) {
		CountryDetail countryDetail = new CountryDetail();
		countryDetail.setCountryCode(countryDetailDto.getIsoCode());
		countryDetail.setCountryName(countryDetailDto.getName());
		countryDetail.setDmlTmst(UtilDatetime.getTimestampNow());
		Currency currency = countryDetailDto.getCurrency();
		if (Objects.nonNull(currency)) {
			CountryCurrency countryCurrency = new CountryCurrency();
			countryCurrency.setCurrencyCode(currency.getCode());
			countryCurrency.setCurrencyName(currency.getName());
			countryCurrency.setCurrencySymbol(currency.getSymbol());
			countryCurrency.setDmlTmst(UtilDatetime.getTimestampNow());
			countryCurrency.setCountryDetail(countryDetail);
			List<CountryCurrency> countryCurrencies = new ArrayList<>();
			countryCurrencies.add(countryCurrency);
			countryDetail.setCountryCurrencies(countryCurrencies);
		}
		List<CurrencyExchangeDto> CurrencyExchangeDtos = countryDetailDto.getCurrencyExchanges();
		if (CollectionUtils.isNotEmpty(CurrencyExchangeDtos)) {
			List<CurrencyExchange> currencyExchanges = new ArrayList<>();
			for (CurrencyExchangeDto currencyExchangeDto : CurrencyExchangeDtos) {
				CurrencyExchange currencyExchange = new CurrencyExchange();
				currencyExchange.setCurrencyCode(currencyExchangeDto.getType());
				currencyExchange.setDmlTmst(UtilDatetime.getTimestampNow());
				currencyExchange.setValue(currencyExchangeDto.getValue());
				currencyExchange.setCountryDetail(countryDetail);
				currencyExchanges.add(currencyExchange);
			}
			countryDetail.setCurrencyExchanges(currencyExchanges);
		}
		return countryDetail;
	}

}
