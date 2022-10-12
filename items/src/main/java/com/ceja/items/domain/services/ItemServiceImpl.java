package com.ceja.items.domain.services;

import ceja.commons.models.entities.products.Product;
import com.ceja.items.domain.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

   @Autowired
   private RestTemplate restClient;

   public List<Item> findAll() {
      List<Product> products = Arrays.asList(this.restClient.getForObject("http://products-service/list", Product[].class));
      return products.stream().map(product -> new Item(product, 1) ).collect(Collectors.toList());
   }

   public Item findById(Long id, Integer quantity) {
      Map<String, String> pathPrams = new HashMap<>();
      pathPrams.put("id", id.toString());
      Product product = this.restClient.getForObject("http://products-service/{id}", Product.class, pathPrams);

      return new Item(product, quantity);
   }

   public Item findByName(String name) {
      Map<String, String> pathPrams = new HashMap<>();
      pathPrams.put("name", name);
      Product product = this.restClient.getForObject("http://products-service/name/{name}", Product.class, pathPrams);

      return new Item(product, 1);
   }

   @Override
   public Product save(Product Product) {
      HttpEntity<Product> body = new HttpEntity<>(Product);
      return restClient.exchange("http://products-service/add", HttpMethod.POST, body, Product.class).getBody();
   }

   @Override
   public Product editProduct(Long id, Product Product) {
      HttpEntity<Product> body = new HttpEntity<>(Product);
      Map<String, String> pathPrams = new HashMap<>();
      pathPrams.put("id", id.toString());

      return restClient.exchange("http://products-service/edit/{id}", HttpMethod.PUT, body, Product.class, pathPrams).getBody();
   }

   @Override
   public void deleteProduct(Long id) {
      Map<String, String> pathPrams = new HashMap<>();
      pathPrams.put("id", id.toString());
      restClient.delete("http://products-service/remove/{id}", pathPrams);
   }

}
