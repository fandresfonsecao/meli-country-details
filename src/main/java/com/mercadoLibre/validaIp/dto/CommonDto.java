package com.mercadoLibre.validaIp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CommonDto {

	@JsonIgnore
	public abstract Object getId();

}
