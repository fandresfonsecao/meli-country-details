package com.mercadoLibre.validaIp.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the country_currency database table.
 * 
 */
@Entity
@Table(name="country_currency")
@NamedQuery(name="CountryCurrency.findAll", query="SELECT c FROM CountryCurrency c")
public class CountryCurrency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="country_currency_id")
	private int countryCurrencyId;

	@Column(name="currency_code")
	private String currencyCode;

	@Column(name="currency_name")
	private String currencyName;

	@Column(name="currency_symbol")
	private String currencySymbol;

	@Column(name="dml_tmst")
	private Timestamp dmlTmst;

	//bi-directional many-to-one association to CountryDetail
	@ManyToOne
	@JoinColumn(name="country_code")
	private CountryDetail countryDetail;

	public CountryCurrency() {
	}

	public int getCountryCurrencyId() {
		return this.countryCurrencyId;
	}

	public void setCountryCurrencyId(int countryCurrencyId) {
		this.countryCurrencyId = countryCurrencyId;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencySymbol() {
		return this.currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public Timestamp getDmlTmst() {
		return this.dmlTmst;
	}

	public void setDmlTmst(Timestamp dmlTmst) {
		this.dmlTmst = dmlTmst;
	}

	public CountryDetail getCountryDetail() {
		return this.countryDetail;
	}

	public void setCountryDetail(CountryDetail countryDetail) {
		this.countryDetail = countryDetail;
	}

}