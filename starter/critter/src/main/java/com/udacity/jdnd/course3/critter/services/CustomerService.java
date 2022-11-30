package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Transactional
    public void addPetsToCustomer(Long customerId, List<Long> petIds) throws OwnerNotFoundException {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);

        if (!optionalCustomer.isPresent()) {
            throw new OwnerNotFoundException();
        }

        List<Pet> pets = this.petRepository.findAllById(petIds);

        Customer customer = optionalCustomer.get();
        customer.getPets().addAll(pets);

        this.customerRepository.save(customer);
    }

    @Transactional
    public Customer getOwnerByPet(Long petId) {
        Optional<Pet> optionalPet = this.petRepository.findById(petId);

        if (!optionalPet.isPresent()) {
            throw new PetNotFoundException();
        }

        return optionalPet.get().getCustomer();
    }

    @Transactional
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }
}
