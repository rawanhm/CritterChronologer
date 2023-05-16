package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name="schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule_employee",
            joinColumns = { @JoinColumn(name = "schedule_id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id") }
    )
    private List<Employee> employee;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule_pet",
            joinColumns = { @JoinColumn(name = "schedule_id") },
            inverseJoinColumns = { @JoinColumn(name = "pet_id") }
    )
    private List<Pet> pets;

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    public Schedule() {
    }

    public Schedule(Long id, List<Employee> employee, List<Pet> pets, LocalDate date, Set<EmployeeSkill> activities) {
        this.id = id;
        this.employee = employee;
        this.pets = pets;
        this.date = date;
        this.activities = activities;
    }

}
