package com.example.todo.controllers;

import com.example.todo.entities.Item;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private ArrayList<Item> itemList = new ArrayList<Item>();
    private static AtomicLong counter = new AtomicLong();

    @GetMapping
    public ArrayList<Item> findAll() {
        return itemList;
    }

    @GetMapping("/{id}")
    public Item find(@PathVariable long id) {
        return findItemById(id);
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        item.setId(counter.incrementAndGet());

        itemList.add(item);

        return item;
    }

    @PutMapping("/{id}")
    public long update(@PathVariable long id, @RequestBody Item item) {
        Item originalItem = findItemById(id);

        if (originalItem != null) {
            originalItem.setName(item.getName());
            originalItem.setDescription(item.getDescription());
        }

        return id;
    }

    @DeleteMapping("/{id}")
    public long delete(@PathVariable long id) {
        itemList.removeIf(item -> item.getId() == id);

        return id;
    }

    private Item findItemById(long id) {
        for(Item item : itemList) {
            if(item.getId() == id) {
                return item;
            }
        }

        return null;
    }
}
