package com.mercadoLibre.validaIp.dto;

import java.io.Serializable;

/**
 * To handle the response from rest service country by IP
 * 
 * @author fabian.fonseca
 */
public class CountryInfoResp implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Country's 2 digit code
	 */
	private String countryCode;
	/**
	 * Country's 3 digit code
	 */
	private String countryCode3;
	/**
	 * Country's name
	 */
	private String countryName;
	/**
	 * Country's emoji
	 */
	private String countryEmoji;

	public CountryInfoResp() {
		super();
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode3() {
		return countryCode3;
	}

	public void setCountryCode3(String countryCode3) {
		this.countryCode3 = countryCode3;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryEmoji() {
		return countryEmoji;
	}

	public void setCountryEmoji(String countryEmoji) {
		this.countryEmoji = countryEmoji;
	}

}
