package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.services.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Transactional
    public void setAvailability(Set<DayOfWeek> daysOfWeek, Long employeeId) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException();
        }

        Employee employee = optionalEmployee.get();
        employee.setDaysAvailable(daysOfWeek);
        employeeRepository.save(employee);
    }

    @Transactional
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);

        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException();
        }

        return optionalEmployee.get();
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Transactional
    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        List<Employee> employees = this.employeeRepository.findAll();

        return employees.stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .filter(employee -> employee.getDaysAvailable().contains(dayOfWeek))
                .collect(Collectors.toList());
    }
}
