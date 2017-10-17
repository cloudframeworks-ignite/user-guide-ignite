
package org.springframework.samples.petclinic.visits.controller;

import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.visits.model.Visit;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.samples.petclinic.visits.service.VisitService;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.validation.Valid;
import java.util.List;

import org.apache.ignite.Ignite;

@RestController
public class VisitController {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
    private VisitService visitService;

    @PostMapping("owners/*/pets/{petId}/visits")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(
        @Valid @RequestBody Visit visit,
        @PathVariable("petId") int petId) {
        visit.petId=petId;
        visit.date=sdf.format(new Date());
    	visitService.add(visit);
    }

    @GetMapping("owners/*/pets/{petId}/visits")
    public List<Visit> visits(@PathVariable("petId") int petId) {
    	//Visit visit=visitService.visitById(1L);
    	//System.out.println("============"+visit);
    	//Visit visit=new Visit(5, 7,"2017-09-04","test");
    	
    	return visitService.visitByPetId(petId);
    }
}
