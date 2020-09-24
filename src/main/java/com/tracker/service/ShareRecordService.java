package com.tracker.service;

import com.tracker.model.Bill;
import com.tracker.model.ShareRecord;
import com.tracker.repo.ShareRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShareRecordService {

    @Autowired
    private ShareRecordRepo shareRecordRepo;

    public List<Bill> getBillsBetweenDates(Date dateFrom, Date dateTo) {
        List<Bill> bills = shareRecordRepo.findAllBillsBetweenDates(dateFrom, dateTo);
        if(bills.size() > 0) {
            return bills;
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<ShareRecord> getAllShareRecords() {
        List<ShareRecord> shareRecords = (List<ShareRecord>)shareRecordRepo.findAll();
        if(shareRecords.size() > 0) {
            return shareRecords;
        }
        else {
            return new ArrayList<>();
        }
    }

}
