package com.tracker.service;

import com.tracker.model.Person;
import com.tracker.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepo personRepo;

    public List<Person> getAllPersons() {
        List<Person> persons = (List<Person>) personRepo.findAll();
        if(persons.size() > 0) {
            return persons;
        }
        else {
            return new ArrayList<>();
        }
    }

    public Person getPersonById(Integer id) {
        Optional<Person> person = personRepo.findById(id);
        if(person.isPresent()) {
            return person.get();
        }
        else {
            System.out.println("No Person record exist");
            return null;
        }
    }

    public String deletePersonById(Integer id) {
        Optional<Person> person = personRepo.findById(id);
        if(person.isPresent()) {
            personRepo.deleteById(id);
            System.out.println("Delete Person "+id+" Completed");
            return "DELETE_SUCCESS";
        }
        else {
            System.out.println("No Person record exist");
            return "DELETE_FAILED";
        }
    }

    public String createOrUpdatePerson(Person person) {
        if(person.getId() == null) {
            personRepo.save(person);
            System.out.println("Person Added");
            return "ADD_SUCCESS";
        }
        else {
            Optional<Person> personById = personRepo.findById(person.getId());
            if(personById.isPresent()) {
                Person newPerson = personById.get();
                newPerson.setName(person.getName());
                newPerson.setEmail(person.getEmail());
                personRepo.save(newPerson);
                System.out.println("Person "+person.getId()+ " Edit Successful");
                return "EDIT_SUCCESS";
            }
            else {
                personRepo.save(person);
                return "ADD_SUCCESS";
            }
        }
    }

}
