package com.mercadoLibre.validaIp.util;

public enum ApplicationPropertyCode {

	COUNTRY_INFO_ENDPOINT("country.info.endpoint"),

	COUNTRY_DETAIL_ENDPOINT("country.detail.endpoint"),

	COUNTRY_CURRENCY_ENDPOINT("country.currency.endpoint"),

	CACHE_IN_SECONDS("cache.seconds"),

	;

	private String code;

	private ApplicationPropertyCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
