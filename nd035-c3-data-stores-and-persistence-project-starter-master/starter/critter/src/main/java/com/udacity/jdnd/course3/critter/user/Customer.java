package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import lombok.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="customer")

@NoArgsConstructor

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nationalized
    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Pet> pets;
    public Customer(long id, String name, String phoneNumber, String notes, List<Pet> pets) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

}
