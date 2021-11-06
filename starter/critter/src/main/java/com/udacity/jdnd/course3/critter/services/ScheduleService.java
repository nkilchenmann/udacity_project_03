package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTOs.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {

        // convert DTO into entity
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);

        // store schedule in DB
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // find saved schedule and return it
        ScheduleDTO savedScheduleDTO = convertScheduleToScheduleDTO(savedSchedule);

        return savedScheduleDTO;
    }

    public List<ScheduleDTO> getAllSchedules() {

        // retrieve all schedules
        List<Schedule> scheduleList = scheduleRepository.findAll();

        // convert all schedules into DTOs
        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(schedule -> convertScheduleToScheduleDTO(schedule)).collect(Collectors.toList());

        // return
        return scheduleDTOList;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();


        // retrieve all linked pets
        List<Long> petList = new ArrayList<>();
        for (Pet pet : schedule.getPets()) {
            petList.add(pet.getId());
        }

        // retrieve all linked employees
        List<Long> employeeList = new ArrayList<>();
        for (Employee employee : schedule.getEmployees()) {
            employeeList.add(employee.getId());
        }

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(employeeList);
        scheduleDTO.setPetIds(petList);
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getSkills());

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        // retrieve all linked pets
        Set<Pet> petSet = new HashSet<>();
        for (Long petId : scheduleDTO.getPetIds()) {
            petSet.add(petRepository.findById(petId).get());
        }

        // retrieve all linked employees
        Set<Employee> employeeSet = new HashSet<>();
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            employeeSet.add(employeeRepository.findById(employeeId).get());
        }

        schedule.setId(scheduleDTO.getId());
        schedule.setEmployees(employeeSet);
        schedule.setPets(petSet);
        schedule.setSkills(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());

        return schedule;
    }
}
