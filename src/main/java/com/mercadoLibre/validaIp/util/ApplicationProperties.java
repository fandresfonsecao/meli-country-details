package com.mercadoLibre.validaIp.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import com.mercadoLibre.validaIp.exception.RestException;
import com.mercadoLibre.validaIp.exception.message.ValidationException;

@Repository
@ApplicationScope
public class ApplicationProperties {

	private Properties properties;

	public String getProperty(ApplicationPropertyCode applicationPropertyCode) {
		if (Objects.isNull(properties)) {
			loadProperties();
		}
		return properties.getProperty(applicationPropertyCode.getCode());
	}

	private void loadProperties() {
		properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			throw new RestException(ValidationException.PROPERTIES_NOT_LOAD);
		}
	}

	public void reloadProperties() {
		properties.clear();
		properties = null;
		loadProperties();
	}

}
