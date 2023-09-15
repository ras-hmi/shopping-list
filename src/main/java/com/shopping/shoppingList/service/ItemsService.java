package com.shopping.shoppingList.service;

import com.shopping.shoppingList.models.Items;
import com.shopping.shoppingList.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ItemsService {

    @Autowired
    ItemsRepository itemsRepository;

    public void updateQuantityService(Integer id, Integer quantity){
        itemsRepository.findById(id).map(shoppingList -> {
            shoppingList.setQuantity(quantity);
            return itemsRepository.save(shoppingList);
        });
    }

    public Long calculateBill() {
        List<Items> allItems= itemsRepository.findAll();
        if (allItems.isEmpty())
            return 0L;
        AtomicReference<Long> billAmount= new AtomicReference<>(0L);
        allItems.forEach(items -> {
            billAmount.updateAndGet(v -> v + (long) items.getPrice() * items.getQuantity());
        });
        return billAmount.get();
    }
}
