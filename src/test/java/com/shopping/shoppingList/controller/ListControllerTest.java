package com.shopping.shoppingList.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.shoppingList.models.Items;
import com.shopping.shoppingList.repositories.ItemsRepository;
import com.shopping.shoppingList.service.ItemsService;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.objectweb.asm.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ListControllerTest {

    private MockMvc mockMvc;

    @Mock
    ItemsService service;

    @Mock
    ItemsRepository repo;

    @InjectMocks
    ListController listController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(listController).build();
    }

    @Test
    public void testShowAllItems() throws Exception {
        Mockito.when(repo.findAll()).thenReturn(List.of(new Items(1, "Mobile", 20000, 2)));
        RequestBuilder request = MockMvcRequestBuilders.get("/items/allItems");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(1)))
                .andDo(MockMvcResultHandlers.print())
        ;

    }

    @Test
    void testItemById() throws Exception {
        Items item = new Items(1, "Mobile", 20000, 2);
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(item));
        RequestBuilder request = MockMvcRequestBuilders.get("/items/itemById/{id}", 1);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(item.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(item.getName())))
        ;


    }

    @Test
    public void testAddItems() throws Exception {
        Items item = new Items(1, "Soap", 50, 5);

        Mockito.when(repo.saveAll(new ArrayList<>())).thenReturn(new ArrayList<>());
        RequestBuilder request = MockMvcRequestBuilders.put("/items/addItems")
                .content(convertJson(List.of(item)))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    private String convertJson(List<Items> list) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}