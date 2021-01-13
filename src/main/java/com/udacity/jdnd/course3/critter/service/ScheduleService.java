package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduledRepository) {
        this.scheduleRepository = scheduledRepository;
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<List<Schedule>> getScheduleForPet(Long petId) {
        return Optional.ofNullable(scheduleRepository.findScheduleForPet(petId));
    }

    public Optional<List<Schedule>> getScheduleForEmployee(Long employeeId) {
        return Optional.ofNullable(scheduleRepository.findScheduleForEmployee(employeeId));
    }

    public Optional<List<Schedule>> getScheduleForCustomer(Long customerId) {
        return Optional.ofNullable(scheduleRepository.findScheduleForCustomer(customerId));
    }

}
