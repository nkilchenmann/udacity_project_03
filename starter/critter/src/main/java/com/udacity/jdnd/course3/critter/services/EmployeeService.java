package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTOs.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.helper.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);

        // save new employee
        employeeRepository.save(employee);

        // return the customerDTO after proper conversion to
        return convertEmployeeToEmployeeDTO(employee);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        return convertEmployeeToEmployeeDTO(employee);
    }

    public void setEmployeeSchedule(Set<DayOfWeek> daysAvailable, Long id) {
        Employee employee = employeeRepository.findById(id).get();
        if (employee == null) {
            throw new ObjectNotFoundException(id, "Employee");
        }
        employee.setDaysAvailable(daysAvailable);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> requiredSkills, LocalDate dateOfService) {

        // retrieve available employees (by skills and date)
        List<Employee> initialEmployeeList = employeeRepository.findDistinctByDaysAvailableAndSkillsIn(dateOfService.getDayOfWeek(), requiredSkills);

        // filter out all employees which do not have ALL skills required to provide the service
        List<Employee> qualifiedAvailableEmployees = new ArrayList<>();
        for (Employee employee : initialEmployeeList) {
            if (employee.getSkills().containsAll(requiredSkills)) {
                qualifiedAvailableEmployees.add(employee);
            }
        }
        return qualifiedAvailableEmployees;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
}
