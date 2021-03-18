package com.mercadoLibre.validaIp.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mercadoLibre.validaIp.persistence.entity.CountryDetail;

public interface CountryDetailFacade extends CrudRepository<CountryDetail, String> {

	@Override
	public long count();

	@Override
	public void delete(CountryDetail countryDetail);

	@Override
	public void deleteAll();

	@Override
	public void deleteAll(Iterable<? extends CountryDetail> countryDetails);

	@Override
	public void deleteById(String countryCode);

	@Override
	public boolean existsById(String countryCode);

	@Override
	public Iterable<CountryDetail> findAll();

	@Override
	public Iterable<CountryDetail> findAllById(Iterable<String> countryCodes);

	@Override
	public Optional<CountryDetail> findById(String countryCode);

	@Override
	public <S extends CountryDetail> S save(S countryDetail);

	@Override
	public <S extends CountryDetail> Iterable<S> saveAll(Iterable<S> countryDetail);

}
