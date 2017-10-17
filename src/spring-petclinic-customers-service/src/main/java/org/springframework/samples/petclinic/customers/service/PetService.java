package org.springframework.samples.petclinic.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.springframework.samples.petclinic.customers.dao.PetDao;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetDao petDao;

    public Pet findOne(long id) {
        Pet pet= petDao.findOne(id);
        pet.type=findPetTypeById(pet.typeId);
        return pet;
    }
    
    public PetType findPetTypeById(long typeId){
    	return petDao.findPetTypeById(typeId);
    }
    
    public void save(Pet pet) {
    	petDao.save(pet);
    }
    
    public List<PetType> findPetTypes(){
    	return petDao.findPetTypes();
    }
    
}
