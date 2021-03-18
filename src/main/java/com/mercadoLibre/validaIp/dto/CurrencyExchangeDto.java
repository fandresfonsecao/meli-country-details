package com.mercadoLibre.validaIp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Representation for the currency exchange.
 * 
 * @author fabian.fonseca
 *
 */
@ApiModel(description = "Representation for the currency exchange.")
public class CurrencyExchangeDto {

	/**
	 * Currency code
	 */
	@ApiModelProperty(value = "Currency code", example = "USD")
	private String code;
	/**
	 * Currency exchange value
	 */
	@ApiModelProperty(value = "Currency exchange value", example = "3560.15")
	private Double value;

	public CurrencyExchangeDto() {
		super();
	}

	public String getType() {
		return code;
	}

	public void setType(String code) {
		this.code = code;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
