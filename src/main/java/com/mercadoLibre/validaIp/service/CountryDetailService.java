package com.mercadoLibre.validaIp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadoLibre.validaIp.dto.CountryDetailDto;
import com.mercadoLibre.validaIp.dto.Envelope;
import com.mercadoLibre.validaIp.impl.CountryDetailImpl;

@RestController
@RequestMapping("/country-detail")
public class CountryDetailService extends CommonRestService implements ICountryDetailService {

	@Autowired
	private CountryDetailImpl countryDetail;

	@Override
	@GetMapping("/{ip}")
	public ResponseEntity<Envelope<CountryDetailDto>> getCountryDetail(@PathVariable String ip,
			@RequestParam(required = false) String[] currencyExchange) {
		Envelope<CountryDetailDto> envelope = new Envelope<CountryDetailDto>(HttpStatus.OK);
		envelope.setData(countryDetail.getCountryDetail(ip, currencyExchange));
		return ResponseEntity.ok(envelope);
	}

}
