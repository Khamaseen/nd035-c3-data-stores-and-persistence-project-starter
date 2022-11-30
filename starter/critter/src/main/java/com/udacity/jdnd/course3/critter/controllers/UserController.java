package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.dtos.AddPetDTO;
import com.udacity.jdnd.course3.critter.controllers.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.controllers.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.controllers.dtos.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());

        if (employeeDTO.getDaysAvailable() != null) {
            employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        }

        return employee;
    }

    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());

        if (employee.getDaysAvailable() != null) {
            employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        }

        return employeeDTO;
    }

    public static Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        return customer;
    }

    public static CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());

        if (customer.getPets() != null) {
            customerDTO.setPetIds(customer.getPets().stream()
                    .map(pet -> pet.getId())
                    .collect(Collectors.toList()));
        }

        if (customer.getNotes() != null) {
            customerDTO.setNotes(customer.getNotes());
        }

        return customerDTO;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return convertCustomerToCustomerDTO(this.customerService.saveCustomer(convertCustomerDTOToCustomer(customerDTO)));
    }

    @PutMapping("/customer/{ownerId}")
    public void addPetsToOwner(@RequestBody AddPetDTO addPetDTO, @PathVariable long ownerId) {
        this.customerService.addPetsToCustomer(ownerId, addPetDTO.getPetIds());
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return this.customerService.getAll().stream()
                .map(UserController::convertCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        return convertCustomerToCustomerDTO(this.customerService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convertEmployeeToEmployeeDTO(this.employeeService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO)));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(this.employeeService.getEmployee(employeeId));
    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees() {
        return this.employeeService.getAllEmployees().stream()
                .map(UserController::convertEmployeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        this.employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return this.employeeService.findEmployeesForService(employeeRequestDTO.getSkills(), employeeRequestDTO.getDate().getDayOfWeek()).stream()
                .map(UserController::convertEmployeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

}
