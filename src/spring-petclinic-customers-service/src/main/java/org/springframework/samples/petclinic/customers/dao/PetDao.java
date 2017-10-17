package org.springframework.samples.petclinic.customers.dao;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.*;

@Repository
public class PetDao {

	@Autowired
	private final IgniteCache<Long, Pet> petCache;

	@Autowired
	private final IgniteCache<Long, PetType> petTypecache;

	@Autowired
	private final Ignite ignite;

	public PetDao(IgniteCache<Long, Pet> petCache,
			IgniteCache<Long, PetType> petTypecache, Ignite ignite) {
		this.petCache = petCache;
		this.petTypecache = petTypecache;
		this.ignite = ignite;
	}

	public Pet findOne(long id) {
		return petCache.get(id);
	}
	
	public Set<Pet> findByOwnerId(long ownerId) {
		Set<Pet> rlist=new HashSet<Pet>();
    	QueryCursor<List<?>> cursor = petCache.query(new SqlFieldsQuery(
                "select id, name, birthDate,typeId,ownerId from Pet where ownerId="+ownerId));
    	for (List<?> row : cursor){
    		rlist.add(new Pet(((Long)row.get(0)).intValue(),(String)row.get(1),(String)row.get(2),(Integer)row.get(3),(Integer)row.get(4)));
    	}
        return rlist;
	}

	public PetType findPetTypeById(long id) {
		return petTypecache.get(id);
	}
	
	public long maxID() {
		long id=0;
		QueryCursor<List<?>> cursor = petCache.query(new SqlFieldsQuery(
				"select max(id) from Pet"));
		for (List<?> row : cursor) {
			id=(Long) row.get(0);
		}
		return id;
	}

	public void save(Pet pet) {
		if(pet.id==null){
			pet.id=maxID()+1;
		}
		petCache.put(pet.id, pet);
	}
	
	public List<PetType> findPetTypes(){
		List<PetType> rlist=new ArrayList<PetType>();
    	QueryCursor<List<?>> cursor = petTypecache.query(new SqlFieldsQuery(
                "select id, name from PetType"));
    	for (List<?> row : cursor){
    		rlist.add(new PetType(((Long)row.get(0)).intValue(),(String)row.get(1)));
    	}
        return rlist;
    }
}
