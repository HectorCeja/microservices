package com.ceja.products.domain.repositories;


import com.ceja.products.domain.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "products")
public interface ProductRepository extends CrudRepository<Product, Long> {

   Product findProductByName(@Param("name") String name);

}
