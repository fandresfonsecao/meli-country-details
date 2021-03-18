package com.mercadoLibre.validaIp.service;

import org.springframework.http.ResponseEntity;

import com.mercadoLibre.validaIp.dto.CountryDetailDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "This operation will get country deatils given a IP")
public interface ICountryDetailService {

	/**
	 * This operation will get the details given a IP, however it will check first
	 * if it's valid then if isn't banned and finally will get details like country,
	 * currency and currency type
	 * 
	 * @param ip
	 *            the IP address we want to consult
	 * @return
	 */
	@ApiOperation(value = "This operation will get the details given a IP, however "
			+ "it will check first if it's valid then if isn't banned and finally"
			+ "will get details like country,  currency and currency type", response = CountryDetailDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully response"),
			@ApiResponse(code = 500, message = "Something went terrible wrong") })
	public ResponseEntity getCountryDetail(
			@ApiParam(name = "ip", type = "String", required = true, example = "1.1.1.1", value = "The IP to get country details") String ip,
			@ApiParam(name = "currencyExchange", type = "String", required = false, example = "USD", value = "Currency to calculate exchange value. If no value is sent it calculates for all allow values", allowableValues = "EUR, USD") String[] currencyExchange);

}
