package com.ceja.products.domain.controllers;

import ceja.commons.models.entities.Product;
import com.ceja.products.domain.applications.ProductApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductController {

   @Autowired
   private ProductApplication productApplication;

   @Autowired
   private Environment environment;

   @Value("${server.port}")
   private Integer port;

   public ProductController(){}

   @GetMapping("/name/{name}")
   public Product findProductByName(@PathVariable("name") String name) {
      Product product = this.productApplication.findByName(name);
      product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
      return product;
   }

   @GetMapping("/{id}")
   public Product findProductById(@PathVariable("id") Long id) throws InterruptedException {
      if(id > 10L) {
         throw new IllegalStateException("The product is not found");
      } else if(id.equals(7L)) {
         TimeUnit.SECONDS.sleep(5L);
      }
      Product product = this.productApplication.findById(id);
      product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
      return product;
   }

   @GetMapping("/list")
   public List<Product> findAll() {
      return this.productApplication.getAll().stream().map(product -> {
         product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
         return product;
      }).collect(Collectors.toList());
   }

   @PostMapping("/add")
   @ResponseStatus(HttpStatus.CREATED)
   public Product save(@RequestBody Product product) {
      return this.productApplication.save(product);
   }

   @PutMapping("/edit/{id}")
   @ResponseStatus(HttpStatus.CREATED)
   public Product editProduct(@PathVariable("id") Long id, @RequestBody Product product) throws Exception {
      return this.productApplication.updateProduct(id, product);
   }

   @DeleteMapping("/remove/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteProduct(@PathVariable("id") Long id) {
      this.productApplication.deleteById(id);
   }

}
