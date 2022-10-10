package com.ceja.items.domain.services;

import ceja.commons.models.entities.Product;
import com.ceja.items.domain.models.Item;

import java.util.List;

public interface ItemService {

   List<Item> findAll();

   Item findById(Long id, Integer quantity);

   Item findByName(String name);

   Product save(Product product);

   Product editProduct(Long id, Product product);

   void deleteProduct(Long id);
}
