package com.ceja.items.domain.services;

import ceja.commons.models.entities.products.Product;
import com.ceja.items.clients.ProductClientREST;
import com.ceja.items.domain.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService {

   @Autowired
   private ProductClientREST productClientREST;

   @Override
   public List<Item> findAll() {
      return productClientREST.getAll().stream().map(Product ->  new Item(Product, 1)).collect(Collectors.toList());
   }

   @Override
   public Item findById(Long id, Integer quantity) {
      return new Item(productClientREST.getById(id), quantity);
   }

   @Override
   public Item findByName(String name) {
      return new Item(productClientREST.getByName(name), 1);
   }

   @Override
   public Product save(Product Product) {
      return  productClientREST.save(Product);
   }

   @Override
   public Product editProduct(Long id, Product Product) {
      return productClientREST.editProduct(id, Product);
   }

   @Override
   public void deleteProduct(Long id) {
      productClientREST.deleteProduct(id);
   }
}
