package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public PetController(PetService petService, CustomerService customerService, ModelMapper modelMapper) {
        this.petService = petService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public PetDTO savePet(@Valid @RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        Pet updated = petService.savePet(pet);
        return convertPetToPetDTO(updated);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable Long petId) {
        Pet pet = petService.getPet(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.getAllPets().stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        return pets.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = modelMapper.map(pet, PetDTO.class);
        petDTO.setBirthDate(pet.getBirthDate().orElse(null));
        petDTO.setNotes(pet.getNotes().orElse(null));
        pet.getCustomer().ifPresent(value -> petDTO.setOwnerId(value.getId()));
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        if (petDTO.getOwnerId() != null) {
            Customer owner = customerService.getCustomer(petDTO.getOwnerId());
            pet.setCustomer(owner);
        }
        return pet;
    }
}
