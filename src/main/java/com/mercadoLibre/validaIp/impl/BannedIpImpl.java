package com.mercadoLibre.validaIp.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.mercadoLibre.validaIp.dto.BannedIpDto;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.NotFoundException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;
import com.mercadoLibre.validaIp.persistence.BanIpFacade;
import com.mercadoLibre.validaIp.util.BannedIpTransformer;
import com.mercadoLibre.validaIp.util.UtilValidation;

@Service
public class BannedIpImpl extends CustomCache<BannedIpDto> {

	@Autowired
	private BanIpFacade banIpFacade;

	@Autowired
	private CacheManager cacheManager;

	public void add(String ip) {
		UtilValidation.validateIp(ip);
		banIpFacade.save(BannedIpTransformer.toBannedIp(ip));
		super.addToCache(get(ip));
	}

	public void delete(String ip) {
		UtilValidation.validateIp(ip);
		banIpFacade.deleteById(ip);
		super.removeFromCache(BannedIpTransformer.toBannedIpDto(ip));
	}

	public BannedIpDto get(String ip) {
		UtilValidation.validateIp(ip);
		BannedIpDto bannedIp = super.getFromCache(BannedIpTransformer.toBannedIpDto(ip));
		if (Objects.isNull(bannedIp)) {
			bannedIp = BannedIpTransformer.toBannedIpDto(banIpFacade.findById(ip).orElseThrow(
					() -> new RestException(NotFoundException.BAN_IP_BY_IP_NOT_FOUND, ip)));
		}
		return bannedIp;
	}

	public List<BannedIpDto> listAll() {
		List<BannedIpDto> bannedIps = new ArrayList<>();
		banIpFacade.findAll().forEach(item -> {
			bannedIps.add(BannedIpTransformer.toBannedIpDto(item));
		});
		return bannedIps;
	}

	public boolean exists(String ip) {
		UtilValidation.validateIp(ip);
		return super.existsInCache(BannedIpTransformer.toBannedIpDto(ip))
				|| banIpFacade.existsById(ip);
	}

	@Override
	protected CacheManager getCacheManager() {
		return cacheManager;
	}

	@Override
	protected String getCacheName() {
		return BannedIpDto.class.getSimpleName();
	}

	/**
	 * Check and throw exception if the IP is banned
	 * 
	 * @param ip
	 */
	public void checkBannedIp(String ip) {
		if (BooleanUtils.isTrue(exists(ip))) {
			throw new RestException(ValidationException.IP_BANNED, ip);
		}
	}

}
