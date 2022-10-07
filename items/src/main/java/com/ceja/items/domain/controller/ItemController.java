package com.ceja.items.domain.controller;

import com.ceja.items.domain.models.Item;
import com.ceja.items.domain.models.ProductDTO;
import com.ceja.items.domain.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class ItemController {

   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   @Qualifier("serviceRestTemplate")
   private ItemService itemService;

   @GetMapping("/list")
   public List<Item> getAll(@RequestParam(name = "name", required = false) String name,
                            @RequestHeader(name = "token-request", required = false) String tokenRequest) {
      System.out.println("request param: " + name);
      System.out.println("request header: " + tokenRequest);
      return itemService.findAll();
   }

   @TimeLimiter(name = "items" , fallbackMethod = "alternativeMethod")
   @CircuitBreaker(name = "items", fallbackMethod = "alternativeMethod")
   @GetMapping("/{id}/detail/quantity/{quantity}")
   public CompletableFuture<Item> detail(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
      return CompletableFuture.supplyAsync(() -> itemService.findById(id, quantity));
   }

   @GetMapping("/name/{name}")
   public Item getByName(@PathVariable("name") String name) {
      return itemService.findByName(name);
   }

   public CompletableFuture<Item> alternativeMethod(Long id, Integer quantity, Throwable exception) {
      logger.info("Product service has failed due to: " + exception.getMessage());
      Item item = new Item();
      ProductDTO productDTO = new ProductDTO();
      productDTO.setName("Sony");
      productDTO.setId(id);
      productDTO.setPrice(500.00);
      item.setQuantity(quantity);
      item.setProduct(productDTO);
      return CompletableFuture.supplyAsync(() -> item);
   }

}
