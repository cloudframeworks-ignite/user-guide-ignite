
package org.springframework.samples.petclinic.vets.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;


public class Vet implements Serializable {

	private static final AtomicLong ID_GEN = new AtomicLong();

	@QuerySqlField(index = true)
	public Long id;
	
	@QuerySqlField
	public String firstName;

	@QuerySqlField
	public String lastName;
	
	public Vet(int id, String firstName, String lastName) {
		this.id = Long.valueOf(id);
		this.firstName = firstName;
		this.lastName = lastName;
	}

}
