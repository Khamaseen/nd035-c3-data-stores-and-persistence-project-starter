package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Modifying
    @Transactional
    public Schedule createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = this.employeeRepository.findAllById(employeeIds);
        List<Pet> pets = this.petRepository.findAllById(petIds);

        Set<Customer> customers = new HashSet<>();
        pets.forEach(pet -> customers.add(pet.getCustomer()));

        schedule.setCustomers(new ArrayList<>(customers));
        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return this.scheduleRepository.save(schedule);
    }

    @Transactional
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Transactional
    public List<Schedule> getAllSchedulesForPet(Long petId) throws PetNotFoundException {
        Optional<Pet> optionalPet = this.petRepository.findById(petId);

        if (!optionalPet.isPresent()) {
            throw new PetNotFoundException();
        }

        return optionalPet.get().getSchedules();
    }

    @Transactional
    public List<Schedule> getAllSchedulesForEmployee(Long employeeId) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);

        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException();
        }

        return optionalEmployee.get().getSchedules();
    }

    @Transactional
    public List<Schedule> getAllSchedulesForCustomer(Long customerId) throws OwnerNotFoundException {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);

        if (!optionalCustomer.isPresent()) {
            throw new OwnerNotFoundException();
        }

        return optionalCustomer.get().getSchedules();
    }
}
