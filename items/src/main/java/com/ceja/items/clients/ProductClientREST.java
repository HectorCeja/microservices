package com.ceja.items.clients;

import com.ceja.items.domain.models.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "products-service")
public interface ProductClientREST {

   @GetMapping("/list")
   List<ProductDTO> getAll();

   @GetMapping("/{id}")
   ProductDTO getById(@PathVariable("id") Long id);

   @GetMapping("/name/{name}")
   ProductDTO getByName(@PathVariable("name") String name);

   @PostMapping("/add")
   ProductDTO save(@RequestBody ProductDTO product);

   @PutMapping("/edit/{id}")
   ProductDTO editProduct(@PathVariable("id") Long id, @RequestBody ProductDTO product);

   @DeleteMapping("/remove/{id}")
   void deleteProduct(@PathVariable("id") Long id);

}
