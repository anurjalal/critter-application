package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    public EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    public void updateEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        employeeRepository.findById(employeeId).ifPresent(employee -> employee.setDaysAvailable(daysAvailable));
    }

    public List<Employee> getEmployeeForService(Set<EmployeeSkill> employeeSkill, DayOfWeek dayOfWeek) {
        Set<DayOfWeek> daysOfWeek = new HashSet<>();
        daysOfWeek.add(dayOfWeek);
        return employeeRepository.findEmployeeForService(employeeSkill, daysOfWeek);
    }
}
