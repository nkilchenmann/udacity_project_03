package com.udacity.jdnd.course3.critter.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {
    @OneToMany(mappedBy = "owner")
    private List<Pet> ownedPets;
    private String phoneNumber;
    private String notes;
}