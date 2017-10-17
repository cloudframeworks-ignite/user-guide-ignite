
package org.springframework.samples.petclinic.customers.model;

import java.util.Date;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.springframework.samples.petclinic.customers.model.PetType;

public class Pet implements Serializable {
	
	private static final AtomicLong ID_GEN = new AtomicLong();

	@QuerySqlField(index = true)
	public Long id;

	@QuerySqlField
    public String name;

	@QuerySqlField
    public String birthDate;

	@QuerySqlField
    public int typeId;

	@QuerySqlField
    public int ownerId;
	
    public PetType type;
	
	public Pet(){
		
	}
	public Pet(int id, String name, String birthDate, int typeId, int ownerId) {
		this.id = Long.valueOf(id);
		this.name = name;
		this.birthDate = birthDate;
		this.typeId = typeId;
		this.ownerId = ownerId;
	}
}
