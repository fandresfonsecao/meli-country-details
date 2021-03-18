package com.mercadoLibre.validaIp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Rest API response
 * 
 * @author fabian.fonseca
 *
 * @param <T>
 */
@JsonInclude(Include.NON_NULL)
@ApiModel(description = "Envelope for all responses", subTypes = { RestError.class })
public class Envelope<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Response code same codes as HttpStatusCode
	 */
	@ApiModelProperty(value = "Response code same codes as HttpStatusCode", example = "200")
	private int statusCode;
	/**
	 * Date and time for the response
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@ApiModelProperty(value = "Date and time for the response", example = "15-03-2021 02:28:41")
	private LocalDateTime transactionTimestamp;
	/**
	 * Response data
	 */
	private T data;

	private RestError error;

	public Envelope() {
		super();
	}

	public Envelope(HttpStatus code) {
		super();
		this.statusCode = code.value();
		this.transactionTimestamp = LocalDateTime.now();
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public LocalDateTime getTransactionTimestamp() {
		return transactionTimestamp;
	}

	public void setTransactionTimestamp(LocalDateTime transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RestError getError() {
		return error;
	}

	public void setError(RestError error) {
		this.error = error;
	}

}
