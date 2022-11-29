package com.udacity.jdnd.course3.critter.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends Person {
    /**
     *     private String phoneNumber;
     *     private String notes;
     *     private List<Long> petIds;
     *
     *     PetIds --> OneToMany relation, JoinColumn
     *     ==> mappedBy ? ..id?
      */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "", cascade = CascadeType.ALL)
    private List<Pet> pets;
}
