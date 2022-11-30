package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    public static PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO(
                pet.getId(),
                pet.getType(),
                pet.getName(),
                pet.getBirthDate(),
                pet.getNotes()
        );

        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;
    }

    public static Pet convertPetDtoToPet(PetDTO petDTO) {
        Pet pet = new Pet(
                petDTO.getId(),
                petDTO.getType(),
                petDTO.getName(),
                petDTO.getBirthDate(),
                petDTO.getNotes()
        );

        return pet;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return convertPetToPetDTO(this.petService.savePet(convertPetDtoToPet(petDTO), petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(this.petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = this.petService.findAllPets();

        return pets.stream().map(PetController::convertPetToPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = this.petService.getAllPetsOfOwner(ownerId);

        return pets.stream().map(PetController::convertPetToPetDTO).collect(Collectors.toList());
    }
}

