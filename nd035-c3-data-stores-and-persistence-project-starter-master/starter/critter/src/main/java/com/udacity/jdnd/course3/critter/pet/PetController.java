package com.udacity.jdnd.course3.critter.pet;


import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        Pet savedPet = petService.savePet(pet);
        petDTO.setId(savedPet.getId());

        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return  convertPetToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> listOfpets =pets.stream().map(p->convertPetToPetDTO(p)).collect(Collectors.toList());
        return  listOfpets;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwnerId(ownerId);
        List<PetDTO> petDTOS = pets.stream().map(p->convertPetToPetDTO(p)).collect(Collectors.toList());
        return petDTOS;
    }
  private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerService.getCustomer(petDTO.getOwnerId()));
        return pet;
    }}
