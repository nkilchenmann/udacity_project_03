package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.helper.EmployeeSkill;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

/**
 * Dummy controller class to verify installation success. Do not use for
 * your project work.
 */
@RestController
@Transactional
public class CritterController {
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/test")
    public String test() {
        return "Critter Starter installed successfully";
    }

    @GetMapping("/addChri")
    public String addChri() {
        Employee employee = new Employee();
        employee.setName("Christine Albornoz");

        Set<DayOfWeek> daysAvailable = new HashSet<>();
        daysAvailable.add(DayOfWeek.MONDAY);
        daysAvailable.add(DayOfWeek.THURSDAY);

        Set<EmployeeSkill> skillSet = new HashSet<>();
        skillSet.add(EmployeeSkill.FEEDING);
        skillSet.add(EmployeeSkill.SHAVING);

        employee.setDaysAvailable(daysAvailable);
        employee.setSkills(skillSet);
        entityManager.persist(employee);


        return "Added Chri";
    }

    @GetMapping("/addNik")
    public String addNik() {
        Employee employee = new Employee();
        employee.setName("Nicolas Kilchenmann");

        Set<DayOfWeek> daysAvailable = new HashSet<>();
        daysAvailable.add(DayOfWeek.TUESDAY);
        daysAvailable.add(DayOfWeek.WEDNESDAY);
        daysAvailable.add(DayOfWeek.FRIDAY);

        Set<EmployeeSkill> skillSet = new HashSet<>();
        skillSet.add(EmployeeSkill.MEDICATING);
        skillSet.add(EmployeeSkill.WALKING);

        employee.setDaysAvailable(daysAvailable);
        employee.setSkills(skillSet);
        entityManager.persist(employee);

        return "Added Nik";
    }
}
