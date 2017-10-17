package org.springframework.samples.petclinic.customers.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;


public class PetType implements Serializable {
	
	private static final AtomicLong ID_GEN = new AtomicLong();

	@QuerySqlField(index = true)
	public Long id;
	
	@QuerySqlField
    public String name;
	
	public PetType(int id, String name){
		this.id = Long.valueOf(id);
		this.name=name;
	}
	
	public PetType(){
		
	}
}
