package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
@Getter @Setter
public class EmployeeDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;

}
