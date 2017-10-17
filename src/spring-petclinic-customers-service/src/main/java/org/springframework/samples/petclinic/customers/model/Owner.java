package org.springframework.samples.petclinic.customers.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.springframework.samples.petclinic.customers.model.Pet;

import java.util.Set;

public class Owner implements Serializable {
	
    private static final AtomicLong ID_GEN = new AtomicLong();

	@QuerySqlField(index = true)
	public Long id;
	
	@QuerySqlField
	public String firstName;

	@QuerySqlField
	public String lastName;
	
	@QuerySqlField
	public String address;
	
	@QuerySqlField
	public String city;
	
	@QuerySqlField
	public String telephone;
	
	public Set<Pet> pets;
	
	public Owner(){
		
	}
	
	public Owner(String firstName, String lastName, String address, String city, String telephone) {
		this.id = ID_GEN.incrementAndGet();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.telephone = telephone;
	}
	
	public Owner(int id, String firstName, String lastName, String address, String city, String telephone) {
		this.id = Long.valueOf(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", address=" + address + ", city=" + city +", telephone=" + telephone +']';
	}
}
