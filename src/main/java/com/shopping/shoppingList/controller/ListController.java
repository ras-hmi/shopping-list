package com.shopping.shoppingList.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.shoppingList.models.Items;
import com.shopping.shoppingList.repositories.ItemsRepository;
import com.shopping.shoppingList.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
//@PreAuthorize("hasRole('ADMIN')")
public class ListController {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private ItemsService itemsService;

    @GetMapping("/allItems")
    public ResponseEntity<?> showAllItems() {
        return ResponseEntity.ok(itemsRepository.findAll());
    }

    @GetMapping("/billAmount")
    public ResponseEntity<?> totalBill(){

        return ResponseEntity.ok("total bill amount: "+itemsService.calculateBill());

    }

    @PutMapping("/addItems")
    public ResponseEntity<?> addInShoppingList(@RequestBody List<Items> items) {
        itemsRepository.saveAll(items);
        return ResponseEntity.ok("items saved");
    }

    @GetMapping("/itemById/{id}")
    public ResponseEntity<?> findbyId(@PathVariable int id) {
        Optional<Items> item = itemsRepository.findById(id);
        if (item.isPresent())
            return ResponseEntity.ok(item.get());
        else
            return ResponseEntity.ok("Item with id: "+id+" is not present");

    }

    @PostMapping("/updateQuantity/{id}")
    public ResponseEntity<?> updateQuantity(@PathVariable Integer id, @RequestParam Integer quantity) {
        itemsService.updateQuantityService(id, quantity);
        return ResponseEntity.ok("Item Saved");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllItems(){
        itemsRepository.deleteAll();
        return ResponseEntity.ok("All Items deleted");
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable Integer id){
        itemsRepository.deleteById(id);
        return ResponseEntity.ok("Item deleted");
    }

}
