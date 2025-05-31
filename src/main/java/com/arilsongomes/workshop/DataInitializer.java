package com.arilsongomes.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ItemRespository itemRespository;

    @Override
    public void run(String... args) throws Exception {

        //item 1
        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setDescription("This is the first item");


        Item item2 = new Item();
        item2.setName("Item 2");
        item2.setDescription("This is the first item");

        itemRespository.save(item1);
        itemRespository.save(item2);
    }
}
