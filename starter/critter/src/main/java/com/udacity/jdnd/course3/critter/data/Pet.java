package com.udacity.jdnd.course3.critter.data;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {
    @Id
    @GeneratedValue
    private Long id;

    /**
     *     private long id;
     *     private PetType type;
     *     private String name;
     *     private long ownerId;
     *     private LocalDate birthDate;
     *     private String notes;
     */



    @Nationalized
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Customer customer;

    private LocalDate birthDate;

    @Nationalized
    @Column(length = 5000)
    private String notes;
}
