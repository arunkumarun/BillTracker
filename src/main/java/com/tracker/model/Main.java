package com.tracker.model;

import com.tracker.util.ShareCalculator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create Person
        Person person1 = new Person();
        person1.setId(1);
        person1.setName("Arun");
        Person person2 = new Person();
        person2.setId(2);
        person2.setName("Varsita");
        Person person3 = new Person();
        person3.setId(3);
        person3.setName("Harshini");


        // Add Persons to a List
        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);


        // Add Items
        Item item1 = new Item();
        item1.setName("Chips");
        item1.setPrice(2.0d);
        item1.setSharingPersons(person1);

        Item item2 = new Item();
        item2.setName("Chips");
        item2.setPrice(2.0d);
        item2.setSharingPersons(person3);

        Item item3 = new Item();
        item3.setName("Potato");
        item3.setPrice(5.0d);
        item3.setSharingPersons(person1);
        item3.setSharingPersons(person2);
        item3.setSharingPersons(person3);

        Item item4 = new Item();
        item4.setName("Chips");
        item4.setPrice(2.0d);
        item4.setSharingPersons(person2);

        // Create New Bill
        Bill aldiBill = new Bill();
        aldiBill.setName("Aldi Bill");
        aldiBill.setDate(new Date());
        aldiBill.setItems(item1);
        aldiBill.setItems(item2);
        aldiBill.setItems(item3);
        aldiBill.calculateTotal();
        PersonPaid pp2 = new PersonPaid();
        pp2.setPerson(person2);
        pp2.setAmount(9.00d);
        /*PersonPaid pp3 = new PersonPaid();
        pp3.setPerson(person3);
        pp3.setAmount(2.00d);
        */
        List<PersonPaid> aldiPaidList = new ArrayList<>();
        aldiPaidList.add(pp2);
        //aldiPaidList.add(pp3);
        aldiBill.setPersonPaids(aldiPaidList);

        /*Bill colesBill = new Bill();
        colesBill.setName("Coles Bill");
        colesBill.setDate(new Date());
        colesBill.setItems(item1);
        colesBill.setItems(item2);
        colesBill.setItems(item4);
        colesBill.calculateTotal();*/
        PersonPaid pp1 = new PersonPaid();
        pp1.setPerson(person1);
        pp1.setAmount(6.00d);
        List<PersonPaid> colesPaidList = new ArrayList<>();
        colesPaidList.add(pp1);
        //colesBill.setPersonPaids(colesPaidList);

        ShareCalculator sc = new ShareCalculator();
        System.out.println(sc.getEachPersonShares(aldiBill));
        //System.out.println(sc.getEachPersonShares(colesBill));

        //System.out.println(colesBill.getAmountToPayEachOther());
        //sc.getAmountToPayEachOther(colesBill);


        //aldiBill.getAmountToPayEachOther();

        System.out.println(aldiBill);
        //System.out.println(colesBill);
    }

}
