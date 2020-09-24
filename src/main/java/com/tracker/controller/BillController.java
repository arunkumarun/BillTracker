package com.tracker.controller;

import com.tracker.model.Bill;
import com.tracker.model.Item;
import com.tracker.model.Person;
import com.tracker.model.PersonPaid;
import com.tracker.service.BillService;
import com.tracker.service.ItemService;
import com.tracker.service.PersonService;
import com.tracker.util.ShareCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private ShareCalculator shareCalculator;

    @Autowired
    private BillService billService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PersonService personService;

    @ModelAttribute("items")
    public List<Item> populateItems() {
        return itemService.getAllItems();
    }

    @ModelAttribute("persons")
    public List<Person> populatePersons() {
        return personService.getAllPersons();
    }

    @GetMapping
    public String bill(Model model) {
        model.addAttribute("bills", billService.getAllBills());
        return "bill/bill";
    }

    @GetMapping("/addBill")
    public String addBill(Model model) {
        Bill formBill = new Bill();
        formBill.getItems().add(new Item());
        formBill.getPersonPaids().add(new PersonPaid());
        model.addAttribute("formBill", formBill);
        return "bill/add-bill";
    }

    @PostMapping("/addBill")
    public String addBill(@ModelAttribute("formBill") @Valid Bill bill, BindingResult br, RedirectAttributes ra) {
        if(br.hasErrors()) {
            return "bill/add-bill";
        }
        bill.setShareInfos(shareCalculator.getEachPersonShares(bill));
        System.out.println(bill);
        String result = billService.createOrUpdateBill(bill);
        if(result.equals("ADD_SUCCESS")) {
            ra.addFlashAttribute("success","Bill Added Successfully");
        }
        else if(result.equals("EDIT_SUCCESS")) {
            ra.addFlashAttribute("success","Bill Edited Successfully");
        }
        return "redirect:/bill";
    }

    @PostMapping(value ="/addBill" ,params = {"addItem"})
    public String addItem(Bill bill, Model model) {
        bill.getItems().add(new Item());
        System.out.println("POST:- addBill -> Added New Item\n"+bill);
        model.addAttribute("formBill", bill);
        return "bill/add-bill";
    }

    @PostMapping(value ="/addBill" ,params = {"removeItem"})
    public String removeItem(Bill bill, HttpServletRequest req, Model model) {
        int rowId = Integer.parseInt(req.getParameter("removeItem"));
        bill.getItems().remove(rowId);
        model.addAttribute("formBill", bill);
        return "bill/add-bill";
    }


    @PostMapping(value ="/addBill" ,params = {"addPersonPaid"})
    public String addPersonPaid(Bill bill, Model model) {
        bill.getPersonPaids().add(new PersonPaid());
        model.addAttribute("formBill", bill);
        return "bill/add-bill";
    }

    @PostMapping(value ="/addBill" ,params = {"removePersonPaid"})
    public String removePersonPaid(Bill bill, HttpServletRequest req, Model model) {
        int rowId = Integer.parseInt(req.getParameter("removeItem"));
        bill.getPersonPaids().remove(rowId);
        model.addAttribute("formBill", bill);
        return "bill/add-bill";
    }

    @PostMapping(value ="/addBill" ,params = {"calculateTotal"})
    @ResponseBody
    public Double calculateTotal(Bill bill) {
        bill.calculateTotal();
        //System.out.println(bill);
        //System.out.println("Total Calculated = "+bill.getTotal());
        return bill.getTotal();
    }

    @GetMapping("/editBill/{id}")
    public String editBill(@PathVariable("id") Integer id, Model model) {
        Bill bill = billService.getBillById(id);
        model.addAttribute("formBill", bill);
        return "bill/add-bill";
    }

    @GetMapping("/deleteBill/{id}")
    public String deleteBill(@PathVariable("id") Integer id, RedirectAttributes ra) {
        String result = billService.deleteBillById(id);
        if(result.equals("DELETE_SUCCESS")) {
            ra.addFlashAttribute("success","Bill has been deleted");
        }
        return "redirect:/bill";
    }

}
