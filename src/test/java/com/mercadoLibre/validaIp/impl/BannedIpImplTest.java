package com.mercadoLibre.validaIp.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.mercadoLibre.validaIp.dto.BannedIpDto;
import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.NotFoundException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;
import com.mercadoLibre.validaIp.persistence.BanIpFacade;
import com.mercadoLibre.validaIp.persistence.entity.BannedIp;
import com.mercadoLibre.validaIp.util.BannedIpTransformer;

class BannedIpImplTest {

	@Mock
	private Cache cache;

	@Mock
	private BanIpFacade banIpFacade;

	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private BannedIpImpl bannedIpImpl;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		cache = Mockito.mock(Cache.class);
		when(cacheManager.getCache(bannedIpImpl.getCacheName())).thenReturn(cache);
	}

	@Test
	void testBannedIpImplGetWithNoData() {
		String ip = "1.1.1.1";
		BannedIpDto bannedIpDto = BannedIpTransformer.toBannedIpDto(ip);
		when(bannedIpImpl.getFromCache(bannedIpDto)).thenReturn(null);
		Exception exception = assertThrows(RestException.class, () -> {
			bannedIpImpl.get(ip);
		}, "No record found");
		assertTrue(
				exception.getMessage()
						.contains(NotFoundException.BAN_IP_BY_IP_NOT_FOUND.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testBannedIpImplGetWithCache() {
		String ip = "1.1.1.1";
		BannedIpDto bannedIpDtoCache = BannedIpTransformer.toBannedIpDto(ip);
		when(bannedIpImpl.getFromCache(bannedIpDtoCache)).thenReturn(bannedIpDtoCache);
		BannedIpDto bannedIpDto = bannedIpImpl.get(ip);
		assertNotNull(bannedIpDto, "Expected not empty object");
		assertNotNull(bannedIpDto.getIp(), "Expected not empty object");
		assertEquals(ip, bannedIpDto.getIp(), "Expected same IP");
		assertNotEquals(ip.concat("1"), bannedIpDto.getIp(), "Expected different IP");
	}

	@Test
	void testBannedIpImplGetNoCache() {
		String ip = "1.1.1.1";
		BannedIpDto bannedIpDtoCache = BannedIpTransformer.toBannedIpDto(ip);
		when(bannedIpImpl.getFromCache(bannedIpDtoCache)).thenReturn(null);
		Optional<BannedIp> bannedIpCache = Optional.of(BannedIpTransformer.toBannedIp(ip));
		when(banIpFacade.findById(ip)).thenReturn(bannedIpCache);
		BannedIpDto bannedIpDto = bannedIpImpl.get(ip);
		assertNotNull(bannedIpDto, "Expected not empty object");
		assertNotNull(bannedIpDto.getIp(), "Expected not empty object");
		assertEquals(ip, bannedIpDto.getIp(), "Expected same IP");
		assertNotEquals(ip.concat("1"), bannedIpDto.getIp(), "Expected different IP");
	}

	@Test
	void testBannedIpImplAddInvalidIp() {
		String ip = "1.1.1";
		Exception exception = assertThrows(RestException.class, () -> {
			bannedIpImpl.get(ip);
		}, "Invalid Ip");
		assertTrue(exception.getMessage().contains(ValidationException.NOT_VALID_IP.getMessage(ip)),
				"Different message in exception throwed");
	}

	@Test
	void testBannedIpImplAdd() {
		String ip = "1.1.1.1";
		BannedIp bannedIp = BannedIpTransformer.toBannedIp(ip);
		BannedIpDto bannedIpDto = BannedIpTransformer.toBannedIpDto(bannedIp);
		when(banIpFacade.save(bannedIp)).thenReturn(bannedIp);
		when(bannedIpImpl.getFromCache(bannedIpDto)).thenReturn(bannedIpDto);
		when(banIpFacade.existsById(ip)).thenReturn(true);
		bannedIpImpl.add(ip);
		assertTrue(bannedIpImpl.exists(ip), "Should exists");
	}

	@Test
	void testBannedIpImplDelete() {
		String ip = "1.1.1.2";
		BannedIp bannedIp = BannedIpTransformer.toBannedIp(ip);
		when(banIpFacade.save(bannedIp)).thenReturn(bannedIp);
		banIpFacade.save(bannedIp);
		bannedIpImpl.delete(ip);
		assertFalse(bannedIpImpl.exists(ip), "Should not exists");
	}

	@Test
	void testBannedIpImplListNoRecords() {
		ArrayList<BannedIp> bannedIps = new ArrayList<BannedIp>();
		when(banIpFacade.findAll()).thenReturn(bannedIps);
		List<BannedIpDto> bannedIpDtos = bannedIpImpl.listAll();
		assertNotNull(bannedIpDtos, "Object is null");
		assertTrue(CollectionUtils.isEmpty(bannedIpDtos), "List is not empty");
	}

	@Test
	void testBannedIpImplList() {
		ArrayList<BannedIp> bannedIps = new ArrayList<BannedIp>();
		bannedIps.add(BannedIpTransformer.toBannedIp("1.1.1.1"));
		bannedIps.add(BannedIpTransformer.toBannedIp("1.1.1.2"));
		bannedIps.add(BannedIpTransformer.toBannedIp("1.1.1.3"));
		when(banIpFacade.findAll()).thenReturn(bannedIps);
		List<BannedIpDto> bannedIpDtos = bannedIpImpl.listAll();
		assertNotNull(bannedIpDtos, "Object is null");
		assertTrue(CollectionUtils.isNotEmpty(bannedIpDtos), "List is empty");
	}

	@Test
	void testBannedIpImplExists() {
		String ip = "1.1.1.1";
		BannedIp bannedIp = BannedIpTransformer.toBannedIp(ip);
		BannedIpDto bannedIpDto = BannedIpTransformer.toBannedIpDto(bannedIp);
		when(cache.get(bannedIpDto.getId(), BannedIpDto.class)).thenReturn(bannedIpDto);
		when(banIpFacade.existsById(bannedIpDto.getIp())).thenReturn(Boolean.TRUE);
		assertTrue(bannedIpImpl.exists(ip), "Should exists");
		assertFalse(bannedIpImpl.exists("1.1.1.2"), "Should not exists");
		String ipNull = null;
		Exception exception = assertThrows(RestException.class, () -> bannedIpImpl.exists(ipNull),
				"Should throws exception");
		assertTrue(
				exception.getMessage()
						.contains(ValidationException.NOT_VALID_IP.getMessage(ipNull)),
				"Different message in exception throwed");
	}

	@Test
	void testBannedIpImplCheckBannedIp() {
		String ip = "1.1.1.1";
		BannedIp bannedIp = BannedIpTransformer.toBannedIp(ip);
		BannedIpDto bannedIpDto = BannedIpTransformer.toBannedIpDto(bannedIp);
		bannedIpImpl.checkBannedIp(ip);
		when(cache.get(bannedIpDto.getId(), BannedIpDto.class)).thenReturn(bannedIpDto);
		when(banIpFacade.existsById(bannedIpDto.getIp())).thenReturn(Boolean.TRUE);
		assertThrows(RestException.class, () -> bannedIpImpl.checkBannedIp(ip),
				ValidationException.IP_BANNED.getMessage(ip));
		String ipNull = null;
		Exception exception = assertThrows(RestException.class,
				() -> bannedIpImpl.checkBannedIp(null),
				ValidationException.IP_BANNED.getMessage(ipNull));
		assertTrue(
				exception.getMessage()
						.contains(ValidationException.NOT_VALID_IP.getMessage(ipNull)),
				"Different message in exception throwed");
	}

}
