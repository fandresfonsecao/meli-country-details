package com.mercadoLibre.validaIp.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the country_detail database table.
 * 
 */
@Entity
@Table(name="country_detail")
@NamedQuery(name="CountryDetail.findAll", query="SELECT c FROM CountryDetail c")
public class CountryDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="country_code")
	private String countryCode;

	@Column(name="country_name")
	private String countryName;

	@Column(name="dml_tmst")
	private Timestamp dmlTmst;

	//bi-directional many-to-one association to CountryCurrency
	@OneToMany(mappedBy="countryDetail")
	private List<CountryCurrency> countryCurrencies;

	//bi-directional many-to-one association to CurrencyExchange
	@OneToMany(mappedBy="countryDetail")
	private List<CurrencyExchange> currencyExchanges;

	public CountryDetail() {
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Timestamp getDmlTmst() {
		return this.dmlTmst;
	}

	public void setDmlTmst(Timestamp dmlTmst) {
		this.dmlTmst = dmlTmst;
	}

	public List<CountryCurrency> getCountryCurrencies() {
		return this.countryCurrencies;
	}

	public void setCountryCurrencies(List<CountryCurrency> countryCurrencies) {
		this.countryCurrencies = countryCurrencies;
	}

	public CountryCurrency addCountryCurrency(CountryCurrency countryCurrency) {
		getCountryCurrencies().add(countryCurrency);
		countryCurrency.setCountryDetail(this);

		return countryCurrency;
	}

	public CountryCurrency removeCountryCurrency(CountryCurrency countryCurrency) {
		getCountryCurrencies().remove(countryCurrency);
		countryCurrency.setCountryDetail(null);

		return countryCurrency;
	}

	public List<CurrencyExchange> getCurrencyExchanges() {
		return this.currencyExchanges;
	}

	public void setCurrencyExchanges(List<CurrencyExchange> currencyExchanges) {
		this.currencyExchanges = currencyExchanges;
	}

	public CurrencyExchange addCurrencyExchange(CurrencyExchange currencyExchange) {
		getCurrencyExchanges().add(currencyExchange);
		currencyExchange.setCountryDetail(this);

		return currencyExchange;
	}

	public CurrencyExchange removeCurrencyExchange(CurrencyExchange currencyExchange) {
		getCurrencyExchanges().remove(currencyExchange);
		currencyExchange.setCountryDetail(null);

		return currencyExchange;
	}

}