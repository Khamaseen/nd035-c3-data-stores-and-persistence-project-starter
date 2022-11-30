package com.udacity.jdnd.course3.critter.controllers.dtos;

import java.util.List;

public class AddPetDTO {
    List<Long> petIds;

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }
}
