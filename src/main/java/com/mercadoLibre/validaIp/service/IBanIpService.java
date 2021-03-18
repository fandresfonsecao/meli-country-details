package com.mercadoLibre.validaIp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mercadoLibre.validaIp.dto.CountryDetailDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/ban-ip")
@Api(value = "This operation allow create, delete and list banned IPs")
public interface IBanIpService {

	@GetMapping()
	@ApiOperation(value = "Lists all banned IPs", response = CountryDetailDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully response"),
			@ApiResponse(code = 500, message = "Something went terrible wrong") })
	public ResponseEntity listBanIp();

	@GetMapping("/{ip}")
	@ApiOperation(value = "Gets specific banned IP", response = CountryDetailDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully response"),
			@ApiResponse(code = 500, message = "Something went terrible wrong") })
	public ResponseEntity getBanIp(@PathVariable String ip);

	@PostMapping("/{ip}")
	@ApiOperation(value = "Inserts an IP tobe banned", response = CountryDetailDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully response"),
			@ApiResponse(code = 500, message = "Something went terrible wrong") })
	public ResponseEntity addBanIp(@PathVariable String ip);

	@DeleteMapping("/{ip}")
	@ApiOperation(value = "Deletes a banned IP", response = CountryDetailDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully response"),
			@ApiResponse(code = 500, message = "Something went terrible wrong") })
	public ResponseEntity deleteBanIp(@PathVariable String ip);

}
