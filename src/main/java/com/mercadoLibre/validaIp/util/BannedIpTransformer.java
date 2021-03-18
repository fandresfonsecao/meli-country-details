package com.mercadoLibre.validaIp.util;

import java.util.Date;

import com.mercadoLibre.validaIp.dto.BannedIpDto;
import com.mercadoLibre.validaIp.persistence.entity.BannedIp;

public class BannedIpTransformer {

	public static BannedIp toBannedIp(String ip) {
		BannedIp banIp = new BannedIp();
		banIp.setIp(ip);
		banIp.setDmlTmst(UtilDatetime.getTimestampNow());
		return banIp;
	}

	public static BannedIpDto toBannedIpDto(BannedIp banIp) {
		BannedIpDto bannedIp = new BannedIpDto();
		bannedIp.setIp(banIp.getIp());
		bannedIp.setDmlTmst(banIp.getDmlTmst());
		return bannedIp;
	}

	public static BannedIpDto toBannedIpDto(String ip) {
		BannedIpDto bannedIp = new BannedIpDto();
		bannedIp.setIp(ip);
		bannedIp.setDmlTmst(new Date());
		return bannedIp;
	}

}
