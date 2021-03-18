package com.mercadoLibre.validaIp.util;

import java.sql.Timestamp;
import java.time.Instant;

public class UtilDatetime {

	public static Timestamp getTimestampNow() {
		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		return timestamp;
	}

}
