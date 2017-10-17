package org.springframework.samples.petclinic.customers.dao;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.List;
import java.util.ArrayList;

@Repository
public class OwnerDao {

	@Autowired
	private final IgniteCache<Long, Owner> cache;

	@Autowired
	private final Ignite ignite;

	public OwnerDao(IgniteCache<Long, Owner> cache, Ignite ignite) {
		this.cache = cache;
		this.ignite = ignite;
	}

	public Owner findOne(long id) {
		return cache.get(id);
	}

	public List<Owner> findAll() {
		List<Owner> rlist = new ArrayList<Owner>();
		QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
				"select * from Owner"));
		// List<List<?>> res = cursor.getAll();
		for (List<?> row : cursor) {
			rlist.add(new Owner(((Long) row.get(0)).intValue(), (String) row
					.get(1), (String) row.get(2), (String) row.get(3),
					(String) row.get(4), (String) row.get(5)));
		}
		return rlist;
	}

	public long maxID() {
		long id=0;
		QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
				"select max(id) from Owner"));
		for (List<?> row : cursor) {
			id=(Long) row.get(0);
		}
		return id;
	}
	
	public void save(Owner owner) {
		if(owner.id==null){
			owner.id=maxID()+1;
		}
		cache.put(owner.id, owner);
	}
}
