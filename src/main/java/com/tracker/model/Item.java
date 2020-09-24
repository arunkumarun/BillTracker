package com.tracker.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    @Size(min=2, max = 30)
    private String name;

    @NotNull
    @PositiveOrZero
    private Double price;

    @ManyToMany(targetEntity = Person.class, cascade = CascadeType.PERSIST)
    @NotEmpty
    @Valid
    private Set<Person> sharingPersons;

    public Item() {
    }

    public Item(@NotNull @Size(min = 2, max = 30) String name, @NotNull @PositiveOrZero Double price, @Valid @NotNull Set<Person> sharingPersons) {
        this.name = name;
        this.price = price;
        this.sharingPersons = sharingPersons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Person> getSharingPersons() {
        return sharingPersons;
    }

    public void setSharingPersons(Person sharingPerson) {
        if (this.sharingPersons == null) {
            this.sharingPersons = new LinkedHashSet<>();
        }
        this.sharingPersons.add(sharingPerson);
    }

    public void setSharingPersons(Set<Person> sharingPersons) {
        this.sharingPersons = sharingPersons;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", sharingPersons=" + sharingPersons +
                '}';
    }
}
