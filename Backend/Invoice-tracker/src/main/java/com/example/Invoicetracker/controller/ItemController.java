package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.controller.entity.ResponseEntity;
import com.example.Invoicetracker.service.ItemService;
import com.example.Invoicetracker.service.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<?> getAllItems() {
        logger.info("Attempt to get all items ");
        return new ResponseEntity<>(itemService.getItems());
    }

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody ItemDTO item) {
        logger.info("Attempt to add item : " + item);
        itemService.addItem(item.getName(), item.getPrice());
        logger.info("Successfully adding item: " + item);
        return new ResponseEntity<>();
    }

}
