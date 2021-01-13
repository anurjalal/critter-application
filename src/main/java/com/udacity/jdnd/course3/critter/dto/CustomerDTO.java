package com.udacity.jdnd.course3.critter.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
@Getter @Setter
public class CustomerDTO {
    private Long id;
    @NotNull
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;
}
