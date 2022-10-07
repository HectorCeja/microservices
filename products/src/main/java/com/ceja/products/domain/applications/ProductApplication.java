package com.ceja.products.domain.applications;

import com.ceja.products.domain.entities.Product;
import com.ceja.products.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ProductApplication {

   @Autowired
   private ProductRepository productRepository;

   public ProductApplication(){}

   @Transactional(readOnly = true)
   public Product findByName(String name) {
      Assert.notNull(name, "Name must not be null");

      return this.productRepository.findProductByName(name);
   }

   @Transactional(readOnly = true)
   public List<Product> getAll() {
      return (List<Product>) this.productRepository.findAll();
   }

   @Transactional(readOnly = true)
   public Product findById(Long id) {
      Optional<Product> product = this.productRepository.findById(id);
      return product.orElseGet(() -> this.productRepository.findById(id).get());
   }

}
