package com.mercadoLibre.validaIp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Currency detail representation
 * 
 * @author fabian.fonseca
 *
 */
@ApiModel(description = "Currency detail representation")
public class Currency {

	/**
	 * Currency name like Canadian dollar
	 */
	@ApiModelProperty(value = "Currency name like Canadian dollar", example = "Colombian peso")
	private String name;
	/**
	 * Currency code like USD
	 */
	@ApiModelProperty(value = "Currency code", example = "COP")
	private String code;
	/**
	 * Currency symbol like $
	 */
	@ApiModelProperty(value = "Currency symbol", example = "$")
	private String symbol;

	public Currency() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
