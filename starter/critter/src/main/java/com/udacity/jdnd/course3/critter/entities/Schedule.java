package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.helper.EmployeeSkill;
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
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "schedule")
    private Set<Employee> employees;

    @ManyToMany(mappedBy = "schedule")
    private Set<Pet> pets;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    private LocalDate date;
}
