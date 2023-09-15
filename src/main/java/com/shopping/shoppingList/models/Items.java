package com.shopping.shoppingList.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "items")
@NoArgsConstructor
public class Items {

    private int id;
    private String name;
    private Integer price;
    private Integer quantity;

    public Items(Integer id,String name,Integer price,Integer quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

}
