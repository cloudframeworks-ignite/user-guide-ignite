package org.springframework.samples.petclinic.customers.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.service.OwnerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@RestController
class OwnerController {

	@Autowired
	private OwnerService ownerService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createOwner(@Valid @RequestBody Owner owner) {
		ownerService.save(owner);
	}

	@GetMapping(value = "/{ownerId}")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return ownerService.findOne(ownerId);
	}

	@GetMapping
	public List<Owner> findAll() {
		return ownerService.findAll();
	}

	@PutMapping(value = "/{ownerId}")
	public Owner updateOwner(@PathVariable("ownerId") int ownerId,
			@Valid @RequestBody Owner ownerRequest) {
		final Owner ownerModel = ownerService.findOne(ownerId);
		ownerModel.firstName = ownerRequest.firstName;
		ownerModel.lastName = ownerRequest.lastName;
		ownerModel.city = ownerRequest.city;
		ownerModel.address = ownerRequest.address;
		ownerModel.telephone = ownerRequest.telephone;
		ownerService.save(ownerModel);
		return ownerModel;
	}
}
