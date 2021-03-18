package com.mercadoLibre.validaIp.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the banned_ip database table.
 * 
 */
@Entity
@Table(name="banned_ip")
@NamedQuery(name="BannedIp.findAll", query="SELECT b FROM BannedIp b")
public class BannedIp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String ip;

	@Column(name="dml_tmst")
	private Timestamp dmlTmst;

	public BannedIp() {
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getDmlTmst() {
		return this.dmlTmst;
	}

	public void setDmlTmst(Timestamp dmlTmst) {
		this.dmlTmst = dmlTmst;
	}

}