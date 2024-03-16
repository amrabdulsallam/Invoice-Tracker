package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.model.Item;
import com.example.Invoicetracker.repository.ItemRepository;
import com.example.Invoicetracker.service.ItemService;
import com.example.Invoicetracker.service.dto.ItemDTO;
import com.example.Invoicetracker.service.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    /**
     * Get All items in the DB
     * @return list of items
     */
    @Override
    public List<ItemDTO> getItems() {
        return itemMapper.itemListToDtos(itemRepository.findAll());
    }

    /**
     * Add a new item
     * @param name item name
     * @param price item price
     */
    @Override
    public void addItem(String name, Double price) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        itemRepository.save(item);
    }

}
