package com.tracker.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ShareRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date dateFrom;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date dateTo;

    @Valid
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Bill> bills;

    @Valid
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<ShareInfo> shareInfos;

    public ShareRecord() {
        this.bills = new ArrayList<>();
        this.shareInfos = new ArrayList<>();
    }

    public ShareRecord(@NotNull Date dateFrom, @NotNull Date dateTo, @Valid List<Bill> bills, @Valid List<ShareInfo> shareInfos) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.bills = bills;
        this.shareInfos = shareInfos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        if(this.bills == null) {
            this.bills = bills;
        }
        else {
            this.bills.clear();
            this.bills.addAll(bills);
        }
    }

    public List<ShareInfo> getShareInfos() {
        return shareInfos;
    }

    public void setShareInfos(List<ShareInfo> shareInfos) {
        this.shareInfos = shareInfos;
    }

    @Override
    public String toString() {
        return "ShareRecord{" +
                "id=" + id +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", bills=" + bills +
                ", shareInfos=" + shareInfos +
                '}';
    }
}
