package com.ceja.items.domain.controller;

import ceja.commons.models.entities.Product;
import com.ceja.items.domain.models.Item;
import com.ceja.items.domain.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ItemController {

   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   private Environment environment;

   @Value("${configuration.text}")
   private String text;

   @Autowired
   @Qualifier("serviceFeign")
   private ItemService itemService;

   @PostMapping("/create")
   @ResponseStatus(HttpStatus.CREATED)
   public Product createProduct(@RequestBody Product Product) {
      return itemService.save(Product);
   }

   @PutMapping("/edit/{id}")
   @ResponseStatus(HttpStatus.CREATED)
   public Product createProduct(@PathVariable("id") Long id, @RequestBody Product Product) {
      return itemService.editProduct(id, Product);
   }

   @DeleteMapping("/delete/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteProduct(@PathVariable("id") Long id) {
      itemService.deleteProduct(id);
   }

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

   @GetMapping("/get-config")
   public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {
      Map<String, String> info = new HashMap<>();
      info.put("text", text);
      info.put("port", port);

      if(environment.getActiveProfiles().length > 0 && Objects.equals(environment.getActiveProfiles()[0], "dev")) {
         info.put("user.name", environment.getProperty("configuration.user.name"));
         info.put("user.email",  environment.getProperty("configuration.user.email"));
      }

      return new ResponseEntity<Map<String, String>>(info, HttpStatus.OK);
   }

   public CompletableFuture<Item> alternativeMethod(Long id, Integer quantity, Throwable exception) {
      logger.info("Product service has failed due to: " + exception.getMessage());
      Item item = new Item();
      Product Product = new Product();
      Product.setName("Sony");
      Product.setId(id);
      Product.setPrice(500.00);
      item.setQuantity(quantity);
      item.setProduct(Product);
      return CompletableFuture.supplyAsync(() -> item);
   }

}
