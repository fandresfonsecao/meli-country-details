package com.mercadoLibre.validaIp.util;

import java.util.Objects;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.UrlValidator;

import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;

/**
 * Holds method which can be useful across the application
 * 
 * @author fabian.fonseca
 *
 */
public class UtilValidation {

	/**
	 * Validates if input IP is valid for v4 or v6. If the validation fails throws
	 * exception
	 * 
	 * @param ip
	 */
	public static void validateIp(String ip) {
		InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
		boolean isValid = StringUtils.isNotBlank(ip)
				&& (inetAddressValidator.isValidInet4Address(ip)
						|| inetAddressValidator.isValidInet6Address(ip));
		if (BooleanUtils.isFalse(isValid)) {
			throw new RestException(ValidationException.NOT_VALID_IP,
					Objects.isNull(ip) ? "null" : ip);
		}
	}

	/**
	 * Validates if input URL is valid. If validation fails throws exception
	 * 
	 * @param url
	 */
	public static void validateUrl(String url) {
		String[] schemes = { "http", "https" };
		UrlValidator urlValidator = new UrlValidator(schemes);
		boolean isValid = StringUtils.isNotBlank(url) && urlValidator.isValid(url);
		if (BooleanUtils.isFalse(isValid)) {
			throw new RestException(ValidationException.NOT_VALID_URL,
					Objects.isNull(url) ? "null" : url);
		}
	}

}
