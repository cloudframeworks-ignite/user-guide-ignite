/**
package org.springframework.samples.petclinic.vets.dao;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.samples.petclinic.vets.model.Specialty;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.List;
import java.util.ArrayList;

@Repository
public class SpecialtyDao {

	@Autowired
	private final IgniteCache<Long, Specialty> specialtyCache;

	@Autowired
	private final Ignite ignite;

	public SpecialtyDao(IgniteCache<Long, Specialty> specialtyCache, Ignite ignite) {
		this.specialtyCache = specialtyCache;
		this.ignite = ignite;
	}

	public Specialty findOne(long id) {
		return specialtyCache.get(id);
	}

	public void save(Specialty specialty) {
		specialtyCache.put(specialty.id, specialty);
	}
}
*/