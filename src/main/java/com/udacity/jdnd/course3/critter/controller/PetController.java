package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        Pet updated = petService.savePet(pet);
        return convertPetToPetDTO(updated);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable Long petId) {
        Pet pet = new Pet();
        if(petService.getPet(petId).isPresent()){
            pet = petService.getPet(petId).get();
        }
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petService.getAllPets();
        ArrayList<PetDTO> petDTOs = new ArrayList<>();
        pets.forEach(pet -> petDTOs.add(convertPetToPetDTO(pet)));
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        Optional<List<Pet>> pets = petService.getPetsByOwner(ownerId);
        ArrayList<PetDTO> petDTOs = new ArrayList<>();
        pets.ifPresent(value -> value.forEach(pet->petDTOs.add(convertPetToPetDTO(pet))));
        return petDTOs;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        pet.getCustomer().ifPresent(value -> petDTO.setOwnerId(value.getId()));
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setName(petDTO.getName());
        pet.setType(petDTO.getType());
        pet.setNotes(petDTO.getNotes());
        if (petDTO.getOwnerId() != null) {
            Optional<Customer> cus = customerService.getCustomer(petDTO.getOwnerId());
            if (cus.isPresent()) {
                Customer owner = cus.get();
                pet.setCustomer(owner);
            }
        }
        return pet;
    }
}
