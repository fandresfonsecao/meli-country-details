package com.mercadoLibre.validaIp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Rest API when an error occur.
 * 
 * @author fabian.fonseca
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "To present an error")
public class RestError implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Error code
	 */
	@ApiModelProperty(value = "Error code", example = "1001")
	private String code;
	/**
	 * Error message
	 */
	@ApiModelProperty(value = "Error message", example = "Something went terrible wrong")
	private String message;
	/**
	 * Exception
	 */
	@ApiModelProperty(value = "Exception", example = "java.lang.NullPointerException")
	private String exception;
	/**
	 * Exception trace
	 */
	@ApiModelProperty(value = "Exception trace")
	private String trace;

	public RestError() {
		super();
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

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

}
