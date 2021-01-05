package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skills")
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "day_available", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "daysAvailable")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Optional<Set<DayOfWeek>> getDaysAvailable() {
        return Optional.ofNullable(daysAvailable);
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
