package com.mercadoLibre.validaIp.dto;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * To handle the response from rest service exchange rates
 * 
 * @author fabian.fonseca
 *
 */
public class RateResp {

	/**
	 * Indicates if it was successful the response
	 */
	private boolean success;
	/**
	 * Date and time for the response
	 */
	private long timestamp;
	/**
	 * Base currency code. Like USD, COP or EUR
	 */
	private String base;
	/**
	 * Date for the response
	 */
	private LocalDate date;
	/**
	 * Rates for given currency codes
	 */
	private Map<String, Double> rates;

	/**
	 * To see what happened if
	 * {@link com.mercadoLibre.validaIp.dto.RateResp.success} is false
	 */
	@JsonProperty("error")
	private RateError rateError;

	public RateResp() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Map<String, Double> getRates() {
		return rates;
	}

	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

	public RateError getRateError() {
		return rateError;
	}

	public void setRateError(RateError rateError) {
		this.rateError = rateError;
	}

}
