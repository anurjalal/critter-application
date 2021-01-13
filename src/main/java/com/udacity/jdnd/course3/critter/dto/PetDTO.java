package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.entity.PetType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
@Getter @Setter
public class PetDTO {
    private Long id;
    @NotNull
    private PetType type;
    @NotNull
    private String name;
    private Long ownerId;
    private LocalDate birthDate;
    private String notes;

}
