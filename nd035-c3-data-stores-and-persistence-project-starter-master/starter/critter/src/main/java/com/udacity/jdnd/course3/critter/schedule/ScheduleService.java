package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepo scheduleRepo;

    @Autowired
    PetRepo petRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    CustomerRepo customerRepo;

    public Schedule save(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }
    public List<Schedule> getAllSchedules(){
        return scheduleRepo.findAll();
    }

    public List<Schedule> getAllSchedulesByPetId(Long id){
        return scheduleRepo.findAllByPetsContaining(petRepo.getOne(id));
    }

    public List<Schedule> getAllSchedulesByEmployeeId(Long id){
        return scheduleRepo.findAllByEmployeeContaining(employeeRepo.getOne(id));
    }

    public List<Schedule> getAllSchedulesByCustomerId(Long id){
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer != null && customer.get() != null) {
            List<Pet> pets = customer.get().getPets();
            List<Schedule> schedules = new ArrayList<>();
            for(Pet p : pets){
                schedules.addAll(scheduleRepo.findAllByPetsContaining(p));
            }
            return schedules;
        } else {
            return null;
        }
    }
}
