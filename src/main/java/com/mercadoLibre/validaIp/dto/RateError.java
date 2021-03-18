package com.mercadoLibre.validaIp.dto;

/**
 * To handle error on exchange rates services
 * 
 * @author fabian.fonseca
 *
 */
public class RateError {
	/**
	 * Error code
	 */
	private Integer code;
	/**
	 * Error description
	 */
	private String type;

	public RateError() {
		super();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
