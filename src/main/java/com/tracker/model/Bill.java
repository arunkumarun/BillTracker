package com.tracker.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    @Size(min=2, max = 30)
    private String name;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date date;

    @NotEmpty
    @Valid
    @OneToMany(targetEntity = Item.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @NotNull
    @PositiveOrZero
    private double total;

    //private Map<Person, Double> personsPaid;

    @NotEmpty
    @Valid
    @OneToMany(targetEntity = PersonPaid.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonPaid> personPaids;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShareInfo> shareInfos;

    public Bill() {
        this.items = new ArrayList<>();
        this.personPaids = new ArrayList<>();
    }

    public Bill(@NotNull @Size(min = 2, max = 30) String name, @NotNull Date date, @NotEmpty @Valid List<Item> items, @NotNull @PositiveOrZero double total, @NotEmpty @Valid List<PersonPaid> personPaids, @Valid List<ShareInfo> shareInfos) {
        this.name = name;
        this.date = date;
        this.items = items;
        this.total = total;
        this.personPaids = personPaids;
        this.shareInfos = shareInfos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        if(this.items == null) {
            this.items = items;
        }
        else {
            this.items.clear();
            this.items.addAll(items);
        }

    }

    public void setItems(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void calculateTotal() {
        total = 0.0d;
        for(Item item: this.items) {
            if(item.getPrice() == null) {
                total += 0.0;
            }
            else {
                total += item.getPrice();
            }
        }
    }

    public List<PersonPaid> getPersonPaids() {
        return personPaids;
    }

    public void setPersonPaids(List<PersonPaid> personPaids) {
        if(this.personPaids == null) {
            this.personPaids = personPaids;
        }
        else {
            this.personPaids.clear();
            this.personPaids.addAll(personPaids);
        }
    }

    public List<ShareInfo> getShareInfos() {
        return shareInfos;
    }

    public void setShareInfos(List<ShareInfo> shareInfos) {
        if(this.shareInfos == null) {
            this.shareInfos = shareInfos;
        }
        else {
            this.shareInfos.clear();
            this.shareInfos.addAll(shareInfos);
        }
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", items=" + items +
                ", total=" + total +
                ", personPaids=" + personPaids +
                ", shareInfos=" + shareInfos +
                '}';
    }
}
