package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.services.exceptions.OwnerNotFoundException;
import com.udacity.jdnd.course3.critter.services.exceptions.PetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

        Pet savedPet = this.petRepository.save(pet);

        // TODO this is not nessecary on SQL, just becuase of the unit tests.. weirdly.
        Customer customer = optionalCustomer.get();
        if (customer.getPets() == null ){
            List<Pet> pets = new ArrayList<Pet>();
            pets.add(savedPet);
            customer.setPets(pets);
            this.customerRepository.save(customer);

            return savedPet;
        }

        if (customer.getPets().contains(savedPet)) {
            return savedPet;
        }
        List<Pet> customerPets = customer.getPets();
        customerPets.add(savedPet);
        this.customerRepository.save(customer);

        return savedPet;
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
