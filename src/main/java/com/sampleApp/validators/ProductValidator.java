package com.sampleApp.validators;

import com.sampleApp.models.Product;


public class ProductValidator {

  public Boolean isValidProduct(Product product) {
    int count = 0;
    if (product == null) {
      return true;
    }
    validateProduct(product, count);

    return count == 0;
  }

  private static void validateProduct(Product product, int count) {
    if (product.getProductTitle().isEmpty() || product.getProductTitle() == null) {
      ++count;
    }
    if (product.getProductDescription().isEmpty() || product.getProductDescription() == null) {
      ++count;
    }
    if (product.getCreatedBy().isEmpty() || product.getCreatedBy() == null) {
      ++count;
    }
    if (product.getPrice() == null || product.getPrice().intValue() <= 0) {
      ++count;
    }
  }
}
