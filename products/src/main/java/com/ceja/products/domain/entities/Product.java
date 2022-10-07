package com.ceja.products.domain.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Product")
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   private Double price;

   @Column(name = "create_at")
   @Temporal(TemporalType.TIMESTAMP)
   private Date createAt;

   @Transient
   private Integer port;

}