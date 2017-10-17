
package org.springframework.samples.petclinic.vets.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.samples.petclinic.vets.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;


@RequestMapping("/vets")
@RestController
class VetController {

	@Autowired
    private VetService vetService;
	
    @GetMapping
    public List<Vet> showResourcesVetList() {
        return vetService.showResourcesVetList();
    }
}
