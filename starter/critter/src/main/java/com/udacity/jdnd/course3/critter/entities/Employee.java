package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.helper.EmployeeSkill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor

/*@NamedQueries(
        @NamedQuery(
                name = "Employee.findByRequiredSkillsetAndDaysAvailable",
                query = "select e from Employee e where e.skills in ?1 and e.daysAvailable in ?2"
        )
)*/

public class Employee extends User {
    @ManyToMany
    private Set<Schedule> schedule;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;
}
