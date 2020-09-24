package com.tracker.model;

import com.tracker.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonCoverter implements Converter<String, Person> {
    @Autowired
    private PersonRepo personRepo;

    @Override
    public Person convert(String s) {
        List<Person> persons = (List<Person>) personRepo.findAll();
        for (Person person: persons) {
            //System.out.println(person.getId()+" == "+s+ "==>"+(person.getId() == Integer.parseInt(s)));
            if (person.getId() == Integer.parseInt(s)) {
                return person;
            }
        }
        return null;
    }
}
