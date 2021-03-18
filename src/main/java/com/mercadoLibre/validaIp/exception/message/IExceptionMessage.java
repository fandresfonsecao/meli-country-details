package com.mercadoLibre.validaIp.exception.message;

import java.text.MessageFormat;

public interface IExceptionMessage {

	public abstract String getCode();

	public abstract String getMessage();

	public default String getMessage(String... value) {
		return MessageFormat.format(getMessage(), (Object[]) value);
	}

}
