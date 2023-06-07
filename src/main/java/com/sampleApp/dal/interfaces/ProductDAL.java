package com.sampleApp.dal.interfaces;

import com.sampleApp.models.Product;

import java.util.List;

public interface ProductDAL {
  Product create(Product product);
  Object delete(String productId);

  List<Product> getAllProducts();
  List<Product> getAllProductsCreatedByLoggedInUser(String userId);

  Object getProductById(String productId);
}
