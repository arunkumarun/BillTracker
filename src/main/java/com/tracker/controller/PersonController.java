package com.tracker.controller;

import com.tracker.model.Person;
import com.tracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public String person(Model model) {
        model.addAttribute("persons", personService.getAllPersons());
        return "person/person";
    }

    @PostMapping("/addPerson")
    public String addPerson(@RequestParam String name, @RequestParam String email, RedirectAttributes ra) {
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);

        String result = personService.createOrUpdatePerson(person);
        if(result.equals("ADD_SUCCESS")) {
            ra.addFlashAttribute("success","Person Added Successfully");
        }
        return "redirect:/person";
    }

    @GetMapping("/editPerson/{id}")
    @ResponseBody
    public Person editPerson(@PathVariable("id") Integer id) {
        Person person = personService.getPersonById(id);
        return person;
    }

    @PostMapping("/editPerson")
    public String editPerson(Person person, RedirectAttributes ra) {
        String result = personService.createOrUpdatePerson(person);
        if(result.equals("EDIT_SUCCESS")) {
            ra.addFlashAttribute("success","Updated Successfully");
        }
        return "redirect:/person";
    }

    @GetMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        String result = personService.deletePersonById(id);
        if(result.equals("DELETE_SUCCESS")) {
            ra.addFlashAttribute("success","Deleted the Person");
        }
        return "redirect:/person";
    }

}
