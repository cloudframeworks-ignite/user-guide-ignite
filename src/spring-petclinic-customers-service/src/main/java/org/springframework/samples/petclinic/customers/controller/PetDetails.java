
package org.springframework.samples.petclinic.customers.controller;

import lombok.Data;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.springframework.samples.petclinic.customers.model.Owner;


@Data
class PetDetails {

    private long id;

    private String name;

    private String owner;
    
    private String birthDate;

    private String type;

    PetDetails(Pet pet, Owner owner, PetType petType) {
        this.id = pet.id;
        this.name = pet.name;
        this.owner = owner.firstName + " " + owner.lastName;
        this.birthDate = pet.birthDate;
        this.type = petType.name;
    }
}
