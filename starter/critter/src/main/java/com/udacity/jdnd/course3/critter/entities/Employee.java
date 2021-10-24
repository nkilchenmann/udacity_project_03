package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Employee extends Person {
    private List<Activity> employeeActivities;
}
