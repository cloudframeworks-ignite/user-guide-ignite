package org.springframework.samples.petclinic.visits.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.visits.model.Visit;
import org.springframework.samples.petclinic.visits.dao.VisitDao;

import java.util.List;

@Service
public class VisitService {
    @Autowired
    private VisitDao visitDao;

    public Visit visitById(long id) {
        return visitDao.visitById(id);
    }
    
    public List<Visit> visitByPetId(int petId) {
        return visitDao.visitByPetId(petId);
    }
    
    public void add(Visit visit) {
    	visitDao.add(visit);
    }
}
