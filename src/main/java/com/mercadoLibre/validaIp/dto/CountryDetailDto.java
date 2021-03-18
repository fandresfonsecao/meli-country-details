package com.mercadoLibre.validaIp.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Response of the principal service which give us details for the country
 * 
 * @author fabian.fonseca
 *
 */
@ApiModel(description = "Response of the principal service which give us details for the country", subTypes = {
		Currency.class, CurrencyExchangeDto.class })
public class CountryDetailDto extends CommonDto {

	/**
	 * Country's name
	 */
	@ApiModelProperty(value = "Country's name", example = "Colombia")
	private String name;
	/**
	 * ISO code (3 digit)
	 */
	@ApiModelProperty(value = "ISO code (3 digit)", example = "COL")
	private String isoCode;
	/**
	 * Country's currency detail
	 */
	private Currency currency;
	/**
	 * Country's currency exchange to foreign currencies
	 */
	private List<CurrencyExchangeDto> currencyExchanges;

	/**
	 * TimeStamp of the last action on the record
	 */
	@ApiModelProperty(value = "TimeStamp of the last action on the record", example = "31-01-2021 02:30:00")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date dmlTmst;

	public CountryDetailDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public List<CurrencyExchangeDto> getCurrencyExchanges() {
		return currencyExchanges;
	}

	public void setCurrencyExchanges(List<CurrencyExchangeDto> currencyExchanges) {
		this.currencyExchanges = currencyExchanges;
	}

	@Override
	public Object getId() {
		return getIsoCode();
	}

	public Date getDmlTmst() {
		return dmlTmst;
	}

	public void setDmlTmst(Date dmlTmst) {
		this.dmlTmst = dmlTmst;
	}

}
