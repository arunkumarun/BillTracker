package com.tracker.service;

import com.tracker.model.Item;
import com.tracker.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    public List<Item> getAllItems() {
        List<Item> items = (List<Item>)itemRepo.findAll();
        if(items.size() > 0) {
            return items;
        }
        else {
            return new ArrayList<>();
        }
    }

    public Item getItemById(Integer id) {
        Optional<Item> item = itemRepo.findById(id);
        if(item.isPresent()) {
            return item.get();
        }
        else {
            System.out.println("No Item record exist");
            return null;
        }
    }

    public String deleteItemById(Integer id) {
        Optional<Item> item = itemRepo.findById(id);
        if(item.isPresent()) {
            itemRepo.deleteById(id);
            System.out.println("Delete Item "+id+" Completed");
            return "DELETE_SUCCESS";
        }
        else {
            System.out.println("No Item record exist");
            return "DELETE_FAILED";
        }
    }

    public String createOrUpdateItem(Item item) {
        if(item.getId() == null) {
            itemRepo.save(item);
            System.out.println("Item Added");
            return "ADD_SUCCESS";
        }
        else {
            Optional<Item> itemById = itemRepo.findById(item.getId());
            if(itemById.isPresent()) {
                Item newItem = itemById.get();
                newItem.setName(item.getName());
                newItem.setPrice(item.getPrice());
                newItem.setSharingPersons(item.getSharingPersons());
                itemRepo.save(newItem);
                System.out.println("Item "+item.getId()+ " Edit Successful");
                return "EDIT_SUCCESS";
            }
            else {
                itemRepo.save(item);
                return "ADD_SUCCESS";
            }
        }
    }

}
