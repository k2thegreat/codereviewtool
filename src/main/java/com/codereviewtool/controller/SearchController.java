package com.codereviewtool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codereviewtool.repo.ItemRepository;
import com.codereviewtool.repo.model.items;

@RestController
@RequestMapping("/groceryItems")
public class SearchController {

    @Autowired
    ItemRepository groceryItemRepo;
    @GetMapping(value = "/search/{name}")
    public String search(@PathVariable("name") String name) {
        return groceryItemRepo.findItemByName(name).toString();
    }

    @PostMapping(value = "/createsample")
    public void createGroceryItems(){
            System.out.println("Data creation started...");
            groceryItemRepo.save(new items("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
            groceryItemRepo.save(new items("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
            groceryItemRepo.save(new items("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
            groceryItemRepo.save(new items("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
            groceryItemRepo.save(new items("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
            System.out.println("Data creation complete...");
    }
}
