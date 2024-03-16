package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.service.ItemService;
import com.example.Invoicetracker.service.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        try {
            logger.info("Attempt to get all items ");
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getItems());
        } catch (Exception e) {
            logger.error("Error while getting all items {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody ItemDTO item) {
        try {
            logger.info("Attempt to add item : " + item);
            itemService.addItem(item.getName(), item.getPrice());
            logger.info("Successfully adding item: " + item);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            logger.error("Error while adding item {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
