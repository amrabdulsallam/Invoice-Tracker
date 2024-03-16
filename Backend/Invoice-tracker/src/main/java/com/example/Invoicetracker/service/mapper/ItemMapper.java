package com.example.Invoicetracker.service.mapper;

import com.example.Invoicetracker.model.Item;
import com.example.Invoicetracker.service.dto.ItemDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    List<Item> dtoToItems(List<ItemDTO> itemDTOS);

    Item dtoToItem(ItemDTO itemDTO);

    List<ItemDTO> itemListToDtos(List<Item> items);

}
