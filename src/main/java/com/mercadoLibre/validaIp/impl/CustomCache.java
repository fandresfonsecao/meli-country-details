package com.mercadoLibre.validaIp.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import com.mercadoLibre.validaIp.dto.CommonDto;

/**
 * Let us control objects in the cache
 * 
 * @author fabian.fonseca
 *
 * @param <T>
 */
@Configuration
@EnableCaching
public abstract class CustomCache<T extends CommonDto> {

	protected abstract String getCacheName();

	/**
	 * Puts an object in the cache
	 * 
	 * @param cacheableDto
	 */
	public void addToCache(T cacheableDto) {
		getCacheManager().getCache(getCacheName()).putIfAbsent(cacheableDto.getId(), cacheableDto);
	}

	/**
	 * Removes an object from the cache
	 * 
	 * @param cacheableDto
	 */
	public void removeFromCache(T cacheableDto) {
		getCacheManager().getCache(getCacheName()).evictIfPresent(cacheableDto.getId());
	}

	/**
	 * Indicates if an object is in the cache
	 * 
	 * @param cacheableDto
	 * @return boolean which indicates that there is and object in the cache
	 */
	public boolean existsInCache(T cacheableDto) {
		return Objects
				.nonNull(getCacheManager().getCache(getCacheName()).get(cacheableDto.getId()));
	}

	/**
	 * Refresh all objects in the cache... Becareful...
	 * 
	 * @param cacheableDtos
	 */
	public void refreshAllCaches(List<T> cacheableDtos) {
		getCacheManager().getCacheNames().stream()
				.forEach(cacheName -> getCacheManager().getCache(cacheName).clear());
		cacheableDtos.forEach(item -> {
			getCacheManager().getCache(getCacheName()).put(item.getId(), item);
		});
	}

	/**
	 * Gets an object from the cache
	 * 
	 * @param cacheableDto
	 * @return The object if it was found or null
	 */
	public T getFromCache(T cacheableDto) {
		return (T) getCacheManager().getCache(getCacheName()).get(cacheableDto.getId(),
				cacheableDto.getClass());
	}

	/**
	 * The subclass has to provide the CacheManager
	 * 
	 * @return
	 */
	protected abstract CacheManager getCacheManager();

}
