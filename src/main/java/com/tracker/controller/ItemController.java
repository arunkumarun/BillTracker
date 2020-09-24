package com.tracker.controller;

import com.tracker.model.Item;
import com.tracker.service.ItemService;
import com.tracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private PersonService personService;

    @GetMapping
    public String item(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("persons", personService.getAllPersons());
        model.addAttribute("formItem", new Item());
        return "item/item";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute("formItem")Item item, RedirectAttributes ra) {
        String result = itemService.createOrUpdateItem(item);
        if(result.equals("ADD_SUCCESS")) {
            ra.addFlashAttribute("success","Item Added Successfully");
        }
        return "redirect:/item";
    }

    @GetMapping("/editItem/{id}")
    @ResponseBody
    public Item editItem(@PathVariable("id") Integer id) {
        Item item = itemService.getItemById(id);
        return item;
    }

    @PostMapping("/editItem")
    public String editItem(Item item, RedirectAttributes ra) {
        System.out.println(item);
        String result = itemService.createOrUpdateItem(item);
        if(result.equals("EDIT_SUCCESS")) {
            ra.addFlashAttribute("success","Item Updated Successfully");
        }
        return "redirect:/item";
    }


    @GetMapping("/deleteItem/{id}")
    public String deleteItem(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        String result = itemService.deleteItemById(id);
        if(result.equals("DELETE_SUCCESS")) {
            ra.addFlashAttribute("success","Item has been deleted");
        }
        return "redirect:/item";
    }

}
