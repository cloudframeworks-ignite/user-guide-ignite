package org.springframework.samples.petclinic.customers.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.web.bind.annotation.*;
import org.springframework.samples.petclinic.customers.service.PetService;
import org.springframework.samples.petclinic.customers.service.OwnerService;

import java.util.List;

@RestController
class PetController {

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private PetService petService;

	@GetMapping("/petTypes")
	public List<PetType> getPetTypes() {
		return petService.findPetTypes();
	}

	@PostMapping("/owners/{ownerId}/pets")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void processCreationForm(@RequestBody PetRequest petRequest,
			@PathVariable("ownerId") int ownerId) {
		Pet pet = new Pet();
		Owner owner = ownerService.findOne(ownerId);
		pet.ownerId=owner.id.intValue();
		save(pet, petRequest);
	}

	@PutMapping("/owners/*/pets/{petId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void processUpdateForm(@RequestBody PetRequest petRequest) {
		save(petService.findOne(petRequest.getId()), petRequest);
	}

	private void save(final Pet pet, final PetRequest petRequest) {
		pet.name=petRequest.getName();
		pet.birthDate=petRequest.getBirthDate();
		pet.typeId=petRequest.getTypeId();
		petService.save(pet);
	}

	@GetMapping("owners/*/pets/{petId}")
	public PetDetails findPet(@PathVariable("petId") int petId) {
		Pet pet = petService.findOne(petId);
		return new PetDetails(pet, ownerService.findOne(pet.ownerId),
				petService.findPetTypeById(pet.typeId));
	}

}
