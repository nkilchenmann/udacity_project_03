package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTOs.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
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

    @Autowired
    CustomerRepository customerRepository;

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {

        // convert DTO into entity
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);

        // add the schedule to all associated employees
        if (scheduleDTO.getEmployeeIds() != null) {
            for (Long employeeId : scheduleDTO.getEmployeeIds()) {
                Employee employee = employeeRepository.findById(employeeId).get();

                // check if set of schedule already contains entries
                Set<Schedule> employeeScheduleSet = new HashSet<>();
                if (employee.getSchedule() == null || employee.getSchedule().isEmpty()) {
                    employeeScheduleSet.add(schedule);
                    employee.setSchedule(employeeScheduleSet);
                } else {
                    employeeScheduleSet = employee.getSchedule();
                    employeeScheduleSet.add(schedule);
                    employee.setSchedule(employeeScheduleSet);
                }
            }
        }

        // add the schedule to all associated pets
        if (scheduleDTO.getPetIds() != null) {
            for (Long petId : scheduleDTO.getPetIds()) {
                Pet pet = petRepository.findById(petId).get();

                // check if set of schedule already contains entries
                Set<Schedule> petScheduleSet = new HashSet<>();
                if (pet.getSchedule() == null || pet.getSchedule().isEmpty()) {
                    petScheduleSet.add(schedule);
                    pet.setSchedule(petScheduleSet);
                } else {
                    petScheduleSet = pet.getSchedule();
                    petScheduleSet.add(schedule);
                    pet.setSchedule(petScheduleSet);
                }
            }
        }

        // store schedule in DB
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // find saved schedule and return it
        ScheduleDTO savedScheduleDTO = convertScheduleToScheduleDTO(savedSchedule);

        // return
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

    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {

        // retrieve all schedules for a given employee
        Employee retrievedEmployee = employeeRepository.findById(employeeId).get();

        // iterate through schedules and convert them to DTO
        List<ScheduleDTO> scheduleDTOList = retrievedEmployee.getSchedule().stream().map(schedule -> convertScheduleToScheduleDTO(schedule)).collect(Collectors.toList());

        // return
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForPet(Long petId) {

        // retrieve all schedules for a given pet
        Pet retrievedPet = petRepository.findById(petId).get();

        // iterate through schedules and convert them to DTO
        List<ScheduleDTO> scheduleDTOList = retrievedPet.getSchedule().stream().map(schedule -> convertScheduleToScheduleDTO(schedule)).collect(Collectors.toList());

        // return
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {

        // retrieve customer from repository
        Customer customer = customerRepository.findById(customerId).get();

        //iterate through the owned pets of the retrieved customer and collect all schedules
        Set<Schedule> scheduleSet = new HashSet<>();
        if (customer.getOwnedPets() != null || !customer.getOwnedPets().isEmpty()) {
            for (Pet pet : customer.getOwnedPets()) {
                scheduleSet.addAll(petRepository.findById(pet.getId()).get().getSchedule());
            }
        }

        // convert to DTOs
        List<ScheduleDTO> scheduleDTOList = scheduleSet.stream().map(schedule -> convertScheduleToScheduleDTO(schedule)).collect(Collectors.toList());

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
