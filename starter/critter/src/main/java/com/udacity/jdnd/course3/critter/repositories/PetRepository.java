package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.data.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p where p.customer.id = :ownerId")
    List<Pet> findAllByOwnerId(Long ownerId);

}

