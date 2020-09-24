package com.tracker.main;

import com.tracker.model.Person;
import com.tracker.repo.PersonRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DBMain {

    private PersonRepo personRepo;

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("password"));
    }

    public void addPersons() {
        Person p1 = new Person();
        p1.setName("Arun");
        p1.setEmail("arun@gmail.com");
        Person p2 = new Person();
        p2.setName("Varsita");
        p1.setEmail("varsita@gmail.com");
        Person p3 = new Person();
        p1.setName("Harshini");
        p1.setEmail("harshini@gmail.com");

        System.out.println(personRepo);
        personRepo.save(p1);
        personRepo.save(p2);
        personRepo.save(p3);

        System.out.println("Added Persons Successfully");
    }

}

