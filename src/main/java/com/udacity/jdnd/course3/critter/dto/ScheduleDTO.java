package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
@Getter @Setter
public class ScheduleDTO {
    private Long id;
    @NotNull
    private List<Long> employeeIds;
    @NotNull
    private List<Long> petIds;
    @NotNull
    private LocalDate date;
    @NotNull
    private Set<EmployeeSkill> activities;

}
