package com.ceja.items.domain.services;

import com.ceja.items.clients.ProductClientREST;
import com.ceja.items.domain.models.Item;
import com.ceja.items.domain.models.ProductDTO;
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
      return productClientREST.getAll().stream().map(productDTO ->  new Item(productDTO, 1)).collect(Collectors.toList());
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
   public ProductDTO save(ProductDTO productDTO) {
      return  productClientREST.save(productDTO);
   }

   @Override
   public ProductDTO editProduct(Long id, ProductDTO productDTO) {
      return productClientREST.editProduct(id, productDTO);
   }

   @Override
   public void deleteProduct(Long id) {
      productClientREST.deleteProduct(id);
   }
}
