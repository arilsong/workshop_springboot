package com.arilsongomes.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ItemController {
    @Autowired
    private ItemRespository itemRespository;


    @GetMapping("/items")
    public List<Item> getALlItem() {
        return itemRespository.findAll();
    }

    @PostMapping("/items")
    public Item createItem(@RequestBody Item item){
        return itemRespository.save(item);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItem(@PathVariable UUID id){
        Optional<Item> item =itemRespository.findById(id);
        if(item.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item.get());
    }
}
