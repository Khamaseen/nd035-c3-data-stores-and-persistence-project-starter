package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Modifying
    @Transactional
    public Pet savePet(Pet pet, Long ownerId) throws OwnerNotFoundException {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(ownerId);
        if (!optionalCustomer.isPresent()) {
            throw new OwnerNotFoundException();
        }

        pet.setCustomer(optionalCustomer.get());

        return this.petRepository.save(pet);
    }

    @Transactional
    public Pet findById(Long petId) throws PetNotFoundException {
        Optional<Pet> optionalPet = this.petRepository.findById(petId);

        if (!optionalPet.isPresent()) {
            throw new PetNotFoundException();
        }

        return optionalPet.get();
    }

    @Transactional
    public List<Pet> findAllPets() {
        return this.petRepository.findAll();
    }

    @Transactional
    public List<Pet> getAllPetsOfOwner(Long ownerId) throws OwnerNotFoundException {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(ownerId);

        if (!optionalCustomer.isPresent()) {
            throw new OwnerNotFoundException();
        }

        return this.petRepository.findAllByOwnerId(ownerId);
    }

}
