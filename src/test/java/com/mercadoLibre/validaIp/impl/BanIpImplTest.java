package com.mercadoLibre.validaIp.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import com.mercadoLibre.validaIp.dto.BannedIpDto;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.NotFoundException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;
import com.mercadoLibre.validaIp.persistence.BanIpFacade;

@SpringBootTest
class BanIpImplTest {

	@Autowired
	private BanIpFacade banIpFacade;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private BannedIpImpl bannedIpImpl;

	@Test
	void testAllIsUp() {
		assertNotNull(banIpFacade, "banIpFacade is null");
		assertNotNull(cacheManager, "cacheManager is null");
	}

	@Test
	void testBannedIpImplGetInvalidIp() {
		String ip = "1.1";
		Exception exception = assertThrows(RestException.class, () -> {
			bannedIpImpl.get(ip);
		}, "Invalid Ip");
		assertTrue(exception.getMessage().contains(ValidationException.NOT_VALID_IP.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testBannedIpImplGetWithNoData() {
		String ip = "1.1.1.1";
		if (bannedIpImpl.exists(ip)) {
			bannedIpImpl.delete(ip);
		}
		Exception exception = assertThrows(RestException.class, () -> {
			bannedIpImpl.get(ip);
		}, "No record found");
		assertTrue(
				exception.getMessage()
						.contains(NotFoundException.BAN_IP_BY_IP_NOT_FOUND.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testBannedIpImplGet() {
		String ip = "1.1.1.1";
		bannedIpImpl.add(ip);
		BannedIpDto bannedIpDto = bannedIpImpl.get(ip);
		assertNotNull(bannedIpDto, "Expected not empty object");
		assertNotNull(bannedIpDto.getIp(), "Expected not empty object");
		assertEquals(ip, bannedIpDto.getIp(), "Expected same IP");
		assertNotEquals(ip.concat("1"), bannedIpDto.getIp(), "Expected different IP");
	}

	@Test
	void testBannedIpImplAddInvalidIp() {
		String ip = "2.2";
		Exception exception = assertThrows(RestException.class, () -> {
			bannedIpImpl.get(ip);
		}, "Invalid Ip");
		assertTrue(exception.getMessage().contains(ValidationException.NOT_VALID_IP.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testBannedIpImplAdd() {
		String ip = "2.2.2.2";
		if (bannedIpImpl.exists(ip)) {
			bannedIpImpl.delete(ip);
		}
		bannedIpImpl.add(ip);
		BannedIpDto bannedIpDto = bannedIpImpl.get(ip);
		assertNotNull(bannedIpDto, "Expected not empty object");
		assertNotNull(bannedIpDto.getIp(), "Expected not empty object");
		assertEquals(ip, bannedIpDto.getIp(), "Expected same IP");
		assertNotEquals(ip.concat("2"), bannedIpDto.getIp(), "Expected different IP");
	}

	@Test
	void testBannedIpImplDeleteInvalidIp() {
		String ip = "3.3";
		Exception exception = assertThrows(RestException.class, () -> {
			bannedIpImpl.get(ip);
		}, "Invalid Ip");
		assertTrue(exception.getMessage().contains(ValidationException.NOT_VALID_IP.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testBannedIpImplDelete() {
		String ip = "3.3.3.3";
		if (bannedIpImpl.exists(ip)) {
			bannedIpImpl.delete(ip);
		}
		bannedIpImpl.add(ip);
		BannedIpDto bannedIpDto = bannedIpImpl.get(ip);
		assertNotNull(bannedIpDto, "Expected not empty object");
		assertNotNull(bannedIpDto.getIp(), "Expected not empty object");
		assertEquals(ip, bannedIpDto.getIp(), "Expected same IP");
		assertNotEquals(ip.concat("3"), bannedIpDto.getIp(), "Expected different IP");
		bannedIpImpl.delete(ip);
		assertFalse(bannedIpImpl.exists(ip));
	}

}
