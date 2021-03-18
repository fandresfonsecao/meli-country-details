package com.mercadoLibre.validaIp.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Ip banned")
public class BannedIpDto extends CommonDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "IP who was banned", example = "1.1.1.1")
	private String ip;
	@ApiModelProperty(value = "Timestamp when the record was inserted", example = "1.1.1.1")
	private Date dmlTmst;

	public BannedIpDto() {
		super();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDmlTmst() {
		return dmlTmst;
	}

	public void setDmlTmst(Date dmlTmst) {
		this.dmlTmst = dmlTmst;
	}

	@Override
	public Object getId() {
		return getIp();
	}

}
