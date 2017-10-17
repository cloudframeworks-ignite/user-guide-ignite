package org.springframework.samples.petclinic.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.dao.OwnerDao;
import org.springframework.samples.petclinic.customers.dao.PetDao;


import java.util.*;

@Service
public class OwnerService {
    @Autowired
    private OwnerDao ownerDao;
    
    @Autowired
    private PetDao petDao;

    public List<Owner> findAll() {
    	List<Owner> ownerList= new ArrayList<Owner>();
    	List<Owner> rlist= ownerDao.findAll();
    	for (Owner owner : rlist) {
    		owner.pets=petDao.findByOwnerId(owner.id);
    		ownerList.add(owner);
    	}
    	return ownerList;
    }
    
    public Owner findOne(int ownerid){
    	Owner owner=ownerDao.findOne(ownerid);
    	
    	Set<Pet> pets =petDao.findByOwnerId(owner.id);
    	Set<Pet> netPetSets=new HashSet<Pet>();
    	for(Pet pet : pets){
    		pet.type=petDao.findPetTypeById(pet.typeId);
    		netPetSets.add(pet);
    	}
    	owner.pets=netPetSets;
    			
    	return owner;
    }
    
    public void save(Owner owner) {
    	ownerDao.save(owner);
    }
}
