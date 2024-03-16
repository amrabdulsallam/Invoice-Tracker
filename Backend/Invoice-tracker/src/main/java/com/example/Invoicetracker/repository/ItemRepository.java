package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByName(String name);

}
