
package org.springframework.samples.petclinic.customers.controller;

import lombok.Data;

import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


@Data
class PetRequest {
    private int id;

    private String birthDate;

    @Size(min = 1)
    private String name;

    private int typeId;
}
