package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    public EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void updateEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            Employee e = employeeRepository.findById(employeeId).get();
            e.setDaysAvailable(daysAvailable);
            employeeRepository.save(e);
        }else{
            throw new EmployeeNotFoundException(employeeId);
        }
    }

    public List<Employee> getEmployeeForService(Set<EmployeeSkill> employeeSkill, DayOfWeek dayOfWeek) {
        List<Employee> employeesAvailabble = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(dayOfWeek);
        for (Employee e : employees) {
            if (e.getDaysAvailable().isPresent()) {
                if (e.getSkills().containsAll(employeeSkill)) {
                    employeesAvailabble.add(e);
                }
            }
        }
        return employeesAvailabble;
    }
}
