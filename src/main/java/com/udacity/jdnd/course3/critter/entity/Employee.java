package com.udacity.jdnd.course3.critter.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @NotNull
    private String name;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skills")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "day_available", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "daysAvailable")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    public Optional<Set<DayOfWeek>> getDaysAvailable() {
        return Optional.ofNullable(daysAvailable);
    }

}
