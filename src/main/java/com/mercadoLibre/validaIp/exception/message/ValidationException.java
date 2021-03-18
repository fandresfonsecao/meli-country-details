package com.mercadoLibre.validaIp.exception.message;

/**
 * Messages when a validation fails
 * 
 * @author fabian.fonseca
 *
 */
public enum ValidationException implements IExceptionMessage {

	PROPERTIES_NOT_LOAD("1001", "Error loading property file"),

	NOT_VALID_IP("1002", "Not valid IP: {0}"),

	EXCHANGE_SERVICE_FAIL("1003",
			"Error on exchange service. Currency code: {0}. Error code {1} and detail: {2}"),

	IP_BANNED("1004", "The IP: {0} is banned")

	;

	private String code;

	private String message;

	private ValidationException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
