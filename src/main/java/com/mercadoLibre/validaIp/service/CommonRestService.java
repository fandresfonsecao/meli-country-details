package com.mercadoLibre.validaIp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.mercadoLibre.validaIp.dto.Envelope;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public class CommonRestService {

	@GetMapping("/status")
	@ApiOperation(value = "Indicates iif the service is up and ready to go", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully response"),
			@ApiResponse(code = 500, message = "Something fail") })
	public ResponseEntity<Envelope<String>> getStatus() {
		Envelope<String> envelope = new Envelope<String>(HttpStatus.OK);
		envelope.setData("Ready");
		return ResponseEntity.ok(envelope);
	}

}
