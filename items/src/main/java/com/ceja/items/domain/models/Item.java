package com.ceja.items.domain.models;

public class Item {

   private ProductDTO product;

   private Integer quantity;

   public Item() {
   }

   public Item(ProductDTO product, Integer quantity) {
      this.product = product;
      this.quantity = quantity;
   }

   public ProductDTO getProduct() {
      return product;
   }

   public void setProduct(ProductDTO product) {
      this.product = product;
   }

   public Integer getQuantity() {
      return quantity;
   }

   public void setQuantity(Integer quantity) {
      this.quantity = quantity;
   }

   public Double getTotal() {
      return product.getPrice() * quantity.doubleValue();
   }

}
