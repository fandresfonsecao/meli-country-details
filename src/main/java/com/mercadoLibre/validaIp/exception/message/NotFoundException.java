package com.mercadoLibre.validaIp.exception.message;

/**
 * Messages when something was not found
 * 
 * @author fabian.fonseca
 *
 */
public enum NotFoundException implements IExceptionMessage {

	COUNTRY_NOT_FOUND("2001", "No country found. Input IP: {0}"),

	CURRENCIES_NOT_FOUND("2002", "No currency found. Searched country: {0}"),

	BAN_IP_BY_IP_NOT_FOUND("2003", "No record found by ip: {0}")

	;

	private String code;

	private String message;

	private NotFoundException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
