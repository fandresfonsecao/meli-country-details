package com.mercadoLibre.validaIp.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the currency_exchange database table.
 * 
 */
@Entity
@Table(name="currency_exchange")
@NamedQuery(name="CurrencyExchange.findAll", query="SELECT c FROM CurrencyExchange c")
public class CurrencyExchange implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="currency_exchange_id")
	private int currencyExchangeId;

	@Column(name="currency_code")
	private String currencyCode;

	@Column(name="dml_tmst")
	private Timestamp dmlTmst;

	private double value;

	//bi-directional many-to-one association to CountryDetail
	@ManyToOne
	@JoinColumn(name="country_code")
	private CountryDetail countryDetail;

	public CurrencyExchange() {
	}

	public int getCurrencyExchangeId() {
		return this.currencyExchangeId;
	}

	public void setCurrencyExchangeId(int currencyExchangeId) {
		this.currencyExchangeId = currencyExchangeId;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Timestamp getDmlTmst() {
		return this.dmlTmst;
	}

	public void setDmlTmst(Timestamp dmlTmst) {
		this.dmlTmst = dmlTmst;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public CountryDetail getCountryDetail() {
		return this.countryDetail;
	}

	public void setCountryDetail(CountryDetail countryDetail) {
		this.countryDetail = countryDetail;
	}

}