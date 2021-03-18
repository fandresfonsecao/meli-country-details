package com.mercadoLibre.validaIp.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

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
	 * Validates the input IP is valid for v4 or v6. If the validation fails throws
	 * exception
	 * 
	 * @param ip
	 */
	public static void validateIp(String ip) {
		InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
		boolean isValid = inetAddressValidator.isValidInet4Address(ip)
				|| inetAddressValidator.isValidInet6Address(ip);
		if (BooleanUtils.isFalse(isValid)) {
			throw new RestException(ValidationException.NOT_VALID_IP, ip);
		}
	}

}
