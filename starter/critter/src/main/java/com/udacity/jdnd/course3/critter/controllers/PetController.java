package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.DTOs.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
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

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        // map DTO to entity
        Pet pet = convertPetDTOToPet(petDTO);

        // save the pet
        petService.savePet(pet, petDTO.getOwnerId());

        // if the petDTO contains an ownerId find the owner in the repository and add the pet to it
        if (petDTO.getOwnerId() != null) {
            customerService.addPetToCustomer(petDTO.getOwnerId(), pet);
        }

        // get the saved pet
        Pet retrievedPet = petService.getPetById(pet.getId());

        // map entity to DTO
        PetDTO retrievedPetDTO = convertPetToPetDTO(retrievedPet);

        return retrievedPetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        // get a Pet by id
        Pet retrievedPet = petService.getPetById(petId);

        // map entity to DTO
        PetDTO retrievedPetDTO = convertPetToPetDTO(retrievedPet);

        return retrievedPetDTO;
    }

    @GetMapping
    public List<PetDTO> getPets() {

        // get pets
        List<Pet> retrievedPetList = petService.getAllPets();

        // map all entities to DTOs
        List<PetDTO> retrievedPetDTOList = retrievedPetList.stream().map(pet -> convertPetToPetDTO(pet)).collect(Collectors.toList());

        return retrievedPetDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        // get pets
        List<Pet> retrievedPetList = petService.getPetsByOwner(ownerId);

        // map all entities to DTOs
        List<PetDTO> retrievedPetDTOList = retrievedPetList.stream().map(pet -> convertPetToPetDTO(pet)).collect(Collectors.toList());

        return retrievedPetDTOList;
    }


    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();

        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getOwner().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());

        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();

        pet.setId(petDTO.getId());
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        return pet;
    }


}
