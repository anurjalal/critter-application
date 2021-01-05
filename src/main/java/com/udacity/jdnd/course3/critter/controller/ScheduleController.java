package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    ScheduleService scheduleService;
    PetService petService;
    EmployeeService employeeService;

    public ScheduleController(ScheduleService scheduleService, PetService petService, EmployeeService employeeService) {
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        Schedule updated = scheduleService.createSchedule(schedule);
        return convertScheduleToScheduleDTO(updated);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable Long petId) {
        Optional<List<Schedule>> schedules = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        schedules.ifPresent(scheduleList -> scheduleDTOs.addAll(scheduleList.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList())));
        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable Long employeeId) {
        Optional<List<Schedule>> schedules = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        schedules.ifPresent(scheduleList -> scheduleDTOs.addAll(scheduleList.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList())));
        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable Long customerId) {
        Optional<List<Schedule>> schedules = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        schedules.ifPresent(scheduleList -> scheduleDTOs.addAll(scheduleList.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList())));
        return scheduleDTOs;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setPetIds(schedule.getPet().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployee().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setPet(scheduleDTO.getPetIds().stream().
                filter(it -> petService.getPet(it).isPresent()).
                map(it -> petService.getPet(it).get()).
                collect(Collectors.toList()));
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setEmployee(scheduleDTO.getEmployeeIds().stream().
                filter(it -> employeeService.getEmployee(it).isPresent()).
                map(it -> employeeService.getEmployee(it).get()).
                collect(Collectors.toList()));
        schedule.setDate(scheduleDTO.getDate());
        return schedule;
    }
}
