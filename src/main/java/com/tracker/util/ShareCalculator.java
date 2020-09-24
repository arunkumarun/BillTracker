package com.tracker.util;

import com.tracker.model.*;
import com.tracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ShareCalculator {

    @Autowired
    private PersonService personService;

    public List<ShareInfo> getEachPersonShares(Bill bill) {
        List<ShareInfo> shareInfos = new ArrayList<>();
        Map<Person, Double> eachPersonShares = calculateEachPersonShares(bill);
        for (Person personShare : eachPersonShares.keySet()) {
            for (PersonPaid pp : bill.getPersonPaids()) {
                if (!personShare.equals(pp.getPerson())) {
                    ShareInfo shareInfo = new ShareInfo();
                    shareInfo.setSender(personShare);
                    shareInfo.setReceiver(pp.getPerson());
                    shareInfo.setAmount(eachPersonShares.get(personShare));

                    shareInfos.add(shareInfo);
                }
            }
        }

        return shareInfos;
    }

    private Map<Person, Double> calculateEachPersonShares(Bill bill) {
        Map<Person, Double> eachPersonShares = new LinkedHashMap<>();

        List<Person> allPersons = personService.getAllPersons();
        allPersons.removeIf(person -> person.getName().contains("GiftCard"));
        allPersons.removeIf(person -> person.getName().equals("Common"));
        System.out.println("All Persons -> " + allPersons);
        Person commonPerson = personService.getPersonById(236);

/*
        Set<Person> personsInBill = new LinkedHashSet<>();
        for (Item item : bill.getItems()) {
            personsInBill.addAll(item.getSharingPersons());
        }
        System.out.println("Person in Persons -> " + personsInBill);
*/


        for (PersonPaid pp : bill.getPersonPaids()) {
            if (pp.getPerson().getName().contains("GiftCard")) {
                for (Item item : bill.getItems()) {
                    double shareAmount = (item.getPrice()) / (item.getSharingPersons().size());
                    for (Person person : item.getSharingPersons()) {
                        if(item.getSharingPersons().size() == allPersons.size()) {
                            double currShareAmount = eachPersonShares.get(commonPerson) == null ? 0 : eachPersonShares.get(commonPerson);
                            eachPersonShares.put(commonPerson, currShareAmount + item.getPrice());
                            break;
                        }
                        else if (eachPersonShares.containsKey(person)) {
                            double currShareAmount = eachPersonShares.get(person);
                            eachPersonShares.put(person, currShareAmount + shareAmount);
                        } else {
                            eachPersonShares.put(person, shareAmount);
                        }
                    }

                }
            } else {
                for (Item item : bill.getItems()) {
                    double shareAmount = (item.getPrice()) / (item.getSharingPersons().size());
                    for (Person person : item.getSharingPersons()) {
                        if (eachPersonShares.containsKey(person)) {
                            double currShareAmount = eachPersonShares.get(person);
                            eachPersonShares.put(person, currShareAmount + shareAmount);
                        } else {
                            eachPersonShares.put(person, shareAmount);
                        }
                    }
                }
            }
        }

        return eachPersonShares;
    }

    public List<ShareInfo> calculateTotalPersonShareFromRecord(List<Bill> bills) {
        List<ShareInfo> results = new ArrayList<>();
        for (Bill bill : bills) {
            for (ShareInfo shareInfo : bill.getShareInfos()) {
                results.add(shareInfo);
            }
        }

        List<ShareInfo> values = results.stream()
                .collect(Collectors.groupingBy(ShareInfo::getSender, Collectors.groupingBy(ShareInfo::getReceiver)))
                .values()
                .stream()
                .map(personListMap -> personListMap.values().stream()
                        .map(shareInfos -> shareInfos.stream().reduce((shareInfo, shareInfo2) -> {
                            ShareInfo si = new ShareInfo();
                            si.setSender(shareInfo.getSender());
                            si.setReceiver(shareInfo.getReceiver());
                            si.setAmount(shareInfo.getAmount() + shareInfo2.getAmount());
                            return si;
                        }).orElse(null)).collect(Collectors.toList()))
                .collect(Collectors.toList())
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println(values);

        System.out.println("------------Results-----------");
        System.out.println(results);
        return values;
    }

    private boolean containsShareInfo(List<ShareInfo> shareInfos, ShareInfo shareInfo) {
        return shareInfos.stream().anyMatch(o -> o.getSender().getName().equals(shareInfo.getSender().getName()) && o.getReceiver().getName().equals(shareInfo.getReceiver().getName()));
    }


    public List<ShareInfo> getAmountToPayEachOther(Bill bill) {
        List<ShareInfo> shareInfos = new ArrayList<>();
        /* Map<Person, Double> eachShares = getEachPersonShares(bill);
        Set<Person> sharePersons = eachShares.keySet();
        Set<Person> paidPersons = bill.getPersonsPaid().keySet();

        if(bill.getPersonsPaid().size() == 1) { //Only one paid the entire amount
            Person receiver = paidPersons.iterator().next();
            for(Person p: sharePersons) {
                //System.out.println(p+"  "+p.isPaid());
                if(!p.equals(receiver)) {
                    ShareInfo shareInfo = new ShareInfo();
                    shareInfo.setReceiver(receiver);
                    shareInfo.setSender(p);
                    shareInfo.setAmount(eachShares.get(receiver));
                    System.out.println(shareInfo);
                    shareInfos.add(shareInfo);
                }
            }
        }
        else {
            for (Person person : paidPersons) {
                double tempPay = this.personsPaid.get(person) - eachShares.get(person);
                //System.out.println(personsPaid.get(person)+" - "+eachShares.get(person));
                if (tempPay > 0) {
                    person.setPaid(true); //Paid the Amount
                    //System.out.println(person + " not need to pay");
                } else {
                    person.setPaid(false); // need to Pay the amount
                    //System.out.println(person + " needed to pay");
                }
            }

            for (Person person : paidPersons) {
                for (Person p : sharePersons) {
                    if (person.isPaid()) {
                        double tempPay = this.personsPaid.get(person) - eachShares.get(person);
                        if (!p.equals(person) && !p.isPaid()) {
                            //System.out.println(p + " -> " + person + " = " + tempPay);
                        }
                    }
                }
            }
        }
        */

        return shareInfos;

    }

}
