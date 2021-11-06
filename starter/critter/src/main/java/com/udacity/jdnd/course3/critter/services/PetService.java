package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet, Long ownerId) {
        pet.setOwner(customerRepository.findById(ownerId).get());
        petRepository.save(pet);
        return pet;
    }

    public Pet getPetById(Long id) {
        Pet pet = petRepository.findById(id).get();
        return pet;
    }

    public List<Pet> getAllPets() {
        List<Pet> petList = petRepository.findAll();
        return petList;
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        List<Pet> retrievedPetList = petRepository.findByOwnerId(ownerId);
        return retrievedPetList;
    }
}
