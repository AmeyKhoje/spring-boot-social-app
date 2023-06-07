package com.sampleApp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("products")
public class Product {

  @Id
  private String productId;
  private String createdBy;
  private String productTitle;
  private String productDescription;
  private Number price;
  private boolean isDiscountAvailable;
  private Number discountedPrice;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getProductDescription() {
    return productDescription;
  }

  public void setProductDescription(String productDescription) {
    this.productDescription = productDescription;
  }

  public Number getPrice() {
    return price;
  }

  public void setPrice(Number price) {
    this.price = price;
  }

  public boolean isDiscountAvailable() {
    return isDiscountAvailable;
  }

  public void setDiscountAvailable(boolean discountAvailable) {
    isDiscountAvailable = discountAvailable;
  }

  public Number getDiscountedPrice() {
    return discountedPrice;
  }

  public void setDiscountedPrice(Number discountedPrice) {
    this.discountedPrice = discountedPrice;
  }
}
