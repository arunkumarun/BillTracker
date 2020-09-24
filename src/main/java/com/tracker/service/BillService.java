package com.tracker.service;

import com.tracker.model.Bill;
import com.tracker.repo.BillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepo billRepo;

    public List<Bill> getAllBills() {
        List<Bill> bills = (List<Bill>)billRepo.findAll();
        if(bills.size() > 0) {
            return bills;
        }
        else {
            return new ArrayList<>();
        }
    }

    public Bill getBillById(Integer id) {
        Optional<Bill> bill = billRepo.findById(id);
        if(bill.isPresent()) {
            return bill.get();
        }
        else {
            System.out.println("No Bill record exist");
            return null;
        }
    }

    public String deleteBillById(Integer id) {
        Optional<Bill> bill = billRepo.findById(id);
        if(bill.isPresent()) {
            billRepo.deleteById(id);
            System.out.println("Delete Bill "+id+" Completed");
            return "DELETE_SUCCESS";
        }
        else {
            System.out.println("No Bill record exist");
            return "DELETE_FAILED";
        }
    }

    public String createOrUpdateBill(Bill bill) {
        if(bill.getId() == null) {
            billRepo.save(bill);
            System.out.println("Bill Added");
            return "ADD_SUCCESS";
        }
        else {
            Optional<Bill> billById = billRepo.findById(bill.getId());
            if(billById.isPresent()) {
                Bill newBill = billById.get();
                newBill.setName(bill.getName());
                newBill.setDate(bill.getDate());
                newBill.setItems(bill.getItems());
                newBill.setTotal(bill.getTotal());
                newBill.setPersonPaids(bill.getPersonPaids());
                newBill.setShareInfos(bill.getShareInfos());

                billRepo.save(newBill);
                System.out.println("Bill "+bill.getId()+ " Edit Successful");
                return "EDIT_SUCCESS";
            }
            else {
                billRepo.save(bill);
                return "ADD_SUCCESS";
            }
        }
    }


}
