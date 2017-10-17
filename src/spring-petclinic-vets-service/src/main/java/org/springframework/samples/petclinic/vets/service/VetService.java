package org.springframework.samples.petclinic.vets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.samples.petclinic.vets.dao.VetDao;

import java.util.List;

@Service
public class VetService {
    @Autowired
    private VetDao vetDao;
    
    public List<Vet> showResourcesVetList() {
    	return vetDao.showResourcesVetList();
    }
    
}
