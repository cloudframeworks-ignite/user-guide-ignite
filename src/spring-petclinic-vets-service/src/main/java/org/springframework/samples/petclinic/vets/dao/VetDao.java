package org.springframework.samples.petclinic.vets.dao;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.samples.petclinic.vets.model.Specialty;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.List;
import java.util.ArrayList;

@Repository
public class VetDao {

	@Autowired
	private final IgniteCache<Long, Vet> vetCache;

	@Autowired
	private final Ignite ignite;

	public VetDao(IgniteCache<Long, Vet> vetCache, Ignite ignite) {
		this.vetCache = vetCache;
		this.ignite = ignite;
	}

	public List<Vet> showResourcesVetList() {
		List<Vet> rlist = new ArrayList<Vet>();
		QueryCursor<List<?>> cursor = vetCache.query(new SqlFieldsQuery(
				"select id, firstName, lastName from Vet"));
		for (List<?> row : cursor) {
			rlist.add(new Vet(((Long) row.get(0)).intValue(), (String) row
					.get(1), (String) row.get(2)));
		}
		return rlist;
	}
}
