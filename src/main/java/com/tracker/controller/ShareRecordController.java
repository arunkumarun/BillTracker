package com.tracker.controller;

import com.tracker.model.Bill;
import com.tracker.model.ShareInfo;
import com.tracker.model.ShareRecord;
import com.tracker.service.ShareRecordService;
import com.tracker.util.ShareCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/record")
public class ShareRecordController {

    @Autowired
    private ShareRecordService shareRecordService;

    @Autowired
    private ShareCalculator shareCalculator;

    @GetMapping
    public String records(Model model) {
        model.addAttribute("records", shareRecordService.getAllShareRecords());
        return "record/record";
    }

    @GetMapping("/addRecord")
    public String addRecord(Model model) {
        ShareRecord formRecord = new ShareRecord();
        model.addAttribute("formRecord", formRecord);
        return "record/add-record";
    }

    @PostMapping(value = "/addRecord", params = {"searchBill"})
    public String searchRecord(ShareRecord shareRecord, Model model) {
        List<Bill> bills = shareRecordService.getBillsBetweenDates(shareRecord.getDateFrom(), shareRecord.getDateTo());
        //System.out.println(bills);
        shareRecord.setBills(bills);
        List<ShareInfo> shareInfos = shareCalculator.calculateTotalPersonShareFromRecord(bills);
        shareRecord.setShareInfos(shareInfos);
        model.addAttribute("formRecord", shareRecord);
        return "record/add-record";
    }

}
