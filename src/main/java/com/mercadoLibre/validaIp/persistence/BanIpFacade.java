package com.mercadoLibre.validaIp.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mercadoLibre.validaIp.persistence.entity.BannedIp;

public interface BanIpFacade extends CrudRepository<BannedIp, String> {

	@Override
	public long count();

	@Override
	public void delete(@Param("banIp") BannedIp banIp);

	@Override
	public void deleteAll();

	@Override
	public void deleteAll(Iterable<? extends BannedIp> arg0);

	@Override
	public void deleteById(@Param("ip") String ip);

	@Override
	public boolean existsById(@Param("ip") String ip);

	@Override
	public Iterable<BannedIp> findAll();

	@Override
	public Iterable<BannedIp> findAllById(@Param("ips") Iterable<String> ips);

	@Override
	public Optional<BannedIp> findById(@Param("ip") String ip);

	@Override
	public <S extends BannedIp> S save(S arg0);

	@Override
	public <S extends BannedIp> Iterable<S> saveAll(Iterable<S> arg0);

}
