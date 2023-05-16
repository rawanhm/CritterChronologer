package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepo petRepo;

    @Autowired
    CustomerRepo customerRepo;


    public Pet savePet(Pet pet) {
        Pet newPet = petRepo.save(pet);
        Customer customer = newPet.getCustomer();
        List<Pet> customerPets = customer.getPets();
        if (customerPets==null) {
            customerPets = new ArrayList<>();
        }
        customerPets.add(newPet);
        customer.setPets(customerPets);
        customerRepo.save(customer);
        return newPet;
    }

    public List<Pet> getAllPets() {
        return petRepo.findAll();
    }

    public Pet getPet(Long id) {
        return petRepo.getOne(id);
    }

    public List<Pet> getPetsByOwnerId(Long id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer != null && customer.get() != null) {
            List<Pet> pets = customer.get().getPets();
            return pets;
        } else {
            return null;
        }
    }

    public List<Pet> findPetsByid(List<Long> id) {
        return petRepo.findAllById(id);
    }
}
