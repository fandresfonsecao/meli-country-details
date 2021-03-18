package com.mercadoLibre.validaIp.exception.message;

/**
 * Messages when something unexpected occurred
 * 
 * @author fabian.fonseca
 *
 */
public enum UnexpectedException implements IExceptionMessage {

	UNEXPECTED_EXCEPTION("0001", "Something went terrible wrong");

	private String code;

	private String message;

	private UnexpectedException(String code, String message) {
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
