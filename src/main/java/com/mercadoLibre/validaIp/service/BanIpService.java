package com.mercadoLibre.validaIp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mercadoLibre.validaIp.dto.BannedIpDto;
import com.mercadoLibre.validaIp.dto.Envelope;
import com.mercadoLibre.validaIp.impl.BannedIpImpl;

@RestController
public class BanIpService extends CommonRestService implements IBanIpService {

	@Autowired
	private BannedIpImpl banIpImpl;

	@Override
	public ResponseEntity listBanIp() {
		Envelope<List<BannedIpDto>> envelope = new Envelope<>(HttpStatus.OK);
		envelope.setData(banIpImpl.listAll());
		return ResponseEntity.ok(envelope);
	}

	@Override
	public ResponseEntity getBanIp(String ip) {
		Envelope<BannedIpDto> envelope = new Envelope<>(HttpStatus.OK);
		envelope.setData(banIpImpl.get(ip));
		return ResponseEntity.ok(envelope);
	}

	@Override
	public ResponseEntity addBanIp(String ip) {
		Envelope envelope = new Envelope<>(HttpStatus.OK);
		banIpImpl.add(ip);
		return ResponseEntity.ok(envelope);
	}

	@Override
	public ResponseEntity deleteBanIp(String ip) {
		Envelope envelope = new Envelope<>(HttpStatus.OK);
		banIpImpl.delete(ip);
		return ResponseEntity.ok(envelope);
	}

}
