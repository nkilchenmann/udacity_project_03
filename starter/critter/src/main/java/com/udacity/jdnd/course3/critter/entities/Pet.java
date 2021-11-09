package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.helper.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private PetType type;

    private LocalDate birthDate;

    private String notes;

    @ManyToOne
    private Customer owner;

    @ManyToMany
    private Set<Schedule> schedule;
}
