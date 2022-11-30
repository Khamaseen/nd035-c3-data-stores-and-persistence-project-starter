package com.udacity.jdnd.course3.critter.data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = {CascadeType.ALL}) // TODO needed: ? : targetEntity = Employee.class,
    @JoinTable(name = "employees_schedules",
            joinColumns = {@JoinColumn(name = "scheule_id")}, // TODO needed: ? correct mapping of names 'schedule_id --> does it map to id?'
            inverseJoinColumns = {@JoinColumn(name = "person_id")})
    private List<Employee> employees;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> involvedSkills;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "pets_schedules",
            joinColumns = {@JoinColumn(name = "scheule_id")},
            inverseJoinColumns = {@JoinColumn(name = "pet_id")})
    private List<Pet> pets;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "customers_schedules",
            joinColumns = {@JoinColumn(name = "scheule_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")})
    private List<Customer> customers;

    private LocalDate date;

    /** --------------------------------------------------- **/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Set<EmployeeSkill> getInvolvedSkills() {
        return involvedSkills;
    }

    public void setInvolvedSkills(Set<EmployeeSkill> involvedSkills) {
        this.involvedSkills = involvedSkills;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

/**
 * private long id;
 * private List<Long> employeeIds;
 * private List<Long> petIds;
 * private LocalDate date;
 * private Set<EmployeeSkill> activities;
 */
