package com.mercadoLibre.validaIp.exception;

import com.mercadoLibre.validaIp.exception.message.IExceptionMessage;

/**
 * Exception for any controlled error
 * 
 * @author fabian.fonseca
 *
 */
public class RestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;

	public RestException(IExceptionMessage exceptionMessage, String... values) {
		super(exceptionMessage.getMessage(values));
		this.code = exceptionMessage.getCode();
		this.message = super.getMessage();
	}

	public RestException(IExceptionMessage exceptionMessage, Throwable cause, String... values) {
		super(exceptionMessage.getMessage(values), cause);
		this.code = exceptionMessage.getCode();
		this.message = super.getMessage();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
