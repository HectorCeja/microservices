package com.ceja.items.clients;

import com.ceja.items.domain.models.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "products-service")
public interface ProductClientREST {

   @GetMapping("/list")
   List<ProductDTO> getAll();

   @GetMapping("/{id}")
   ProductDTO getById(@PathVariable("id") Long id);

   @GetMapping("/name/{name}")
   ProductDTO getByName(@PathVariable("name") String name);

}
