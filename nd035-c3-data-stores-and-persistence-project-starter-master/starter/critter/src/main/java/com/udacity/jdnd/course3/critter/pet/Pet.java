package com.udacity.jdnd.course3.critter.pet;


import com.udacity.jdnd.course3.critter.user.Customer;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@Entity
@Table(name="pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PetType type;

    @Nationalized
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer customer;

    private LocalDate birthDate;
    private String notes;

    public Pet() {
    }

    public Pet(Long id, PetType type, String name, Customer customer, LocalDate birthDate, String notes) {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
    }


}