package com.udacity.jdnd.course3.critter.data;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer extends Person {

    private String phoneNumber;

    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Pet> pets;

    @ManyToMany(mappedBy = "customers")
    private List<Schedule> schedules;

    /**
     * ---------------------------------------------------
     **/

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

}

/**
 * private String phoneNumber;
 * private String notes;
 * private List<Long> petIds;
 */
