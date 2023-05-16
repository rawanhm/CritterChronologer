package com.udacity.jdnd.course3.critter.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Data
@Table(name="employee")
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nationalized
    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    public Employee(long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }
}
