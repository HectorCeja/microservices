package com.ceja.items.clients;

import ceja.commons.models.entities.products.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "products-service")
public interface ProductClientREST {

   @GetMapping("/list")
   List<Product> getAll();

   @GetMapping("/{id}")
   Product getById(@PathVariable("id") Long id);

   @GetMapping("/name/{name}")
   Product getByName(@PathVariable("name") String name);

   @PostMapping("/add")
   Product save(@RequestBody Product product);

   @PutMapping("/edit/{id}")
   Product editProduct(@PathVariable("id") Long id, @RequestBody Product product);

   @DeleteMapping("/remove/{id}")
   void deleteProduct(@PathVariable("id") Long id);

}
