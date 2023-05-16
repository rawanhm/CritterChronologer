package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        Schedule savedSchedule = scheduleService.save(schedule);
        scheduleDTO.setId(savedSchedule.getId());
        return scheduleDTO ;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByPetId(petId);
        return schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByEmployeeId(employeeId);
        return schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByCustomerId(customerId);
        return schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
    }
    //convert Schedule To DTO
    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> petIds;
        if(schedule.getPets() !=null){
            petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        } else {
            petIds = new ArrayList<>();
        }
        scheduleDTO.setPetIds(petIds);

        List<Long> employeeIds;
        if(schedule.getEmployee() !=null){
            employeeIds = schedule.getEmployee().stream().map(Employee::getId).collect(Collectors.toList());
        } else {
            employeeIds = new ArrayList<>();
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }
    //convert  DTO To Schedule
    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setPets(petService.findPetsByid(scheduleDTO.getPetIds()));
        schedule.setEmployee(employeeService.findEmployeesByid(scheduleDTO.getEmployeeIds()));
        return schedule;
    }
}
