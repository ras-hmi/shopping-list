package com.shopping.shoppingList.service;

import com.shopping.shoppingList.models.Items;
import com.shopping.shoppingList.repositories.ItemsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class ItemsServiceTest {

    @InjectMocks
    ItemsService service;

    @Mock
    ItemsRepository repo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void calculateBill() {

        Items item = new Items(1, "Shampoo", 150, 1);
        Mockito.when(repo.findAll()).thenReturn(List.of(item));
        assertEquals(150, service.calculateBill());

    }
}