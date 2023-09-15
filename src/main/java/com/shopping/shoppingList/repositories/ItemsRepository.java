package com.shopping.shoppingList.repositories;

import com.shopping.shoppingList.models.Items;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemsRepository extends MongoRepository<Items, Integer> {
}
