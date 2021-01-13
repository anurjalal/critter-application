package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet savePet(Pet pet) {
        if (pet.getCustomer().isPresent()) {
            Customer cus = pet.getCustomer().get();
            cus.addPet(pet);
        }
        return petRepository.save(pet);
    }

    public Optional<Pet> getPet(Long id) {
        return petRepository.findById(id);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Optional<List<Pet>> getPetsByOwner(Long ownerId) {
        return Optional.ofNullable(petRepository.findPetsByOwner(ownerId));
    }
}
