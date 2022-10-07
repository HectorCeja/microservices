package com.ceja.items.domain.services;

import com.ceja.items.domain.models.Item;
import com.ceja.items.domain.models.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

   @Autowired
   private RestTemplate restClient;

   public List<Item> findAll() {
      List<ProductDTO> products = Arrays.asList(this.restClient.getForObject("http://products-service/list", ProductDTO[].class));
      return products.stream().map(product -> new Item(product, 1) ).collect(Collectors.toList());
   }

   public Item findById(Long id, Integer quantity) {
      Map<String, String> pathPrams = new HashMap<>();
      pathPrams.put("id", id.toString());
      ProductDTO product = this.restClient.getForObject("http://products-service/{id}", ProductDTO.class, pathPrams);

      return new Item(product, quantity);
   }

   public Item findByName(String name) {
      Map<String, String> pathPrams = new HashMap<>();
      pathPrams.put("name", name);
      ProductDTO product = this.restClient.getForObject("http://products-service/name/{name}", ProductDTO.class, pathPrams);

      return new Item(product, 1);
   }

}
