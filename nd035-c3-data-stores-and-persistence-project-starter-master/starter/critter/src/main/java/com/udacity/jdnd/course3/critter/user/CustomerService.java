package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
//@Transactional
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    PetRepo petRepo;
    public Customer saveCustomer(Customer customer) {

        return customerRepo.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getCustomer(Long id) {
        return customerRepo.getOne(id);
    }

    public Customer getCustomerByPetId(Long petId){
        return petRepo.getOne(petId).getCustomer();
    }
    public void deleteCustomer(Long id){
        customerRepo.deleteById(id);
    }
}
