package com.example.Invoicetracker.service;

import com.example.Invoicetracker.service.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getItems();

    void addItem(String name, Double price);

}
