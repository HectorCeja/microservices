package com.ceja.items.domain.services;

import com.ceja.items.domain.models.Item;
import com.ceja.items.domain.models.ProductDTO;

import java.util.List;

public interface ItemService {

   public List<Item> findAll();

   public Item findById(Long id, Integer quantity);

   public Item findByName(String name);

   ProductDTO save(ProductDTO productDTO);

   ProductDTO editProduct(Long id, ProductDTO productDTO);

   void deleteProduct(Long id);
}
